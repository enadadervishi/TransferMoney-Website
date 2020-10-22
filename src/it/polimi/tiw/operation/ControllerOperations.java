package it.polimi.tiw.operation;

import it.polimi.tiw.auth.AuthManager;
import it.polimi.tiw.dao.Database;
import it.polimi.tiw.dao.DbConnection;
import it.polimi.tiw.exceptions.HandleException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ControllerOperations implements AutoCloseable {

    private final Database database;

    public ControllerOperations(Database database) {
        this.database = database;
    }

    public static ControllerOperations createDefault() throws ServletException {
        try {
            return new ControllerOperations(new Database(DbConnection.getConnection()));
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public void run(HttpServletRequest request, HttpServletResponse response,
                    Operation operation, OperationFlag... flags)
            throws ServletException, IOException {
        try {
            Set<OperationFlag> flagSet = new HashSet<>();
            Collections.addAll(flagSet, flags);

            AuthManager authManager = AuthManager.fromRequest(request);

            if (flagSet.contains(OperationFlag.REQUIRE_LOGGED_IN) && !authManager.getConnectedUserId().isPresent()) {
                response.sendRedirect("login");
                return;
            }

            Result result = operation.run(database, authManager);
            result.execute(request, response, authManager);
        } catch (ServletException | IOException e) {
            throw e;
        } catch (HandleException e) {
            response.sendError(e.getErrorCode(), e.getMessage());
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Db error: " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unknown error: " + t.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        database.close();
    }
}
