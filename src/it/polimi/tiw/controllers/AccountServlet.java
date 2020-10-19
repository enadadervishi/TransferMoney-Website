package it.polimi.tiw.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.exceptions.ModelNotFoundException;
import it.polimi.tiw.exceptions.PermissionException;
import it.polimi.tiw.models.Account;
import it.polimi.tiw.operation.ControllerOperations;
import it.polimi.tiw.operation.OperationFlag;
import it.polimi.tiw.operation.RenderAccountPageResult;
import it.polimi.tiw.operation.RequestParams;
import it.polimi.tiw.util.Closeables;

import java.io.IOException;

@WebServlet(name = "AccountServlet", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {

    private ControllerOperations operations = null;

    public void init() throws ServletException {
        operations = ControllerOperations.createDefault();
    }

    public void destroy() {
        Closeables.closeSilently(operations);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        operations.run(request, response, (database, auth)-> {
            try {
                RequestParams params = new RequestParams(request);
                long accountId = params.getLong("id");

                Account account = database.getAccounts().selectById(accountId);
                auth.checkAccountAccess(account);

                return new RenderAccountPageResult(database, account);
            } catch (ModelNotFoundException e) {
                throw new PermissionException("no access to account");
            }
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }
}
