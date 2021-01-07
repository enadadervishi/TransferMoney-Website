package it.polimi.tiw.controllers;

import com.google.gson.Gson;
import it.polimi.tiw.exceptions.ModelNotFoundException;
import it.polimi.tiw.exceptions.PermissionException;
import it.polimi.tiw.models.Account;
import it.polimi.tiw.operation.ControllerOperations;
import it.polimi.tiw.operation.JsonResult;
import it.polimi.tiw.operation.OperationFlag;
import it.polimi.tiw.operation.RequestPath;
import it.polimi.tiw.operation.model.Response;
import it.polimi.tiw.util.Closeables;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AccountServlet", urlPatterns = {"/account/*"})
public class AccountServlet extends HttpServlet {//account | account/10

    private final Gson gson = new Gson();
    private ControllerOperations operations = null;

    public void init() throws ServletException {
        operations = ControllerOperations.createDefault();
    }

    public void destroy() {
        Closeables.closeSilently(operations);
    }

    // GET
    // account
    // account/id
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        operations.run(request, response, (database, auth)-> {
            try {
                long userId = auth.getConnectedUserId().getAsLong();

                RequestPath requestPath = new RequestPath(request);
                if (!requestPath.hasParams()) {
                   List<Account> accounts = database.getAccounts().selectByUserId(userId);
                   return new JsonResult(gson, Response.success(accounts));
                }

                if(requestPath.getPathCount() != 2) {
                	return new JsonResult(gson, Response.error("expected two params: " + requestPath));
                }

                long accountId = requestPath.getLongParam(1);

                Account account = database.getAccounts().selectById(accountId);
                auth.checkAccountAccess(account);

                return new JsonResult(gson, Response.success(account));
            } catch (ModelNotFoundException e) {
                throw new PermissionException("no access to account");
            }
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }

    // POST
    // account
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        operations.run(request, response, (database, auth)-> {
            long userId = auth.getConnectedUserId().getAsLong();

            RequestPath requestPath = new RequestPath(request);
            if (requestPath.hasParams()) {
            	return new JsonResult(gson, Response.error("wrong path"));
            }

            Account account = new Account();
            account.setBalance(50.0);
            account.setUserId(userId);

            account = database.getAccounts().add(account);

            return new JsonResult(gson, Response.success(account));
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }

    // DELETE
    // account/id
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        operations.run(request, response, (database, auth)-> {
            try {
            	
                RequestPath requestPath = new RequestPath(request);
                
                if(requestPath.getPathCount() != 2) {
                    return new JsonResult(gson, Response.error("expected two params"));
                }

                long accountId = requestPath.getLongParam(1);

                Account account = database.getAccounts().selectById(accountId);
                auth.checkAccountAccess(account);
                database.getAccounts().delete(account);

                return new JsonResult(gson, Response.success());
            } catch (ModelNotFoundException e) {
                throw new PermissionException("no access to account");
            }
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }
}
