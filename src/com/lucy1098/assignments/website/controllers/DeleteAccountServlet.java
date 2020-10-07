package com.lucy1098.assignments.website.controllers;

import com.lucy1098.assignments.website.exceptions.ModelNotFoundException;
import com.lucy1098.assignments.website.exceptions.PermissionException;
import com.lucy1098.assignments.website.models.Account;
import com.lucy1098.assignments.website.operation.ControllerOperations;
import com.lucy1098.assignments.website.operation.OperationFlag;
import com.lucy1098.assignments.website.operation.RedirectResult;
import com.lucy1098.assignments.website.operation.RequestParams;
import com.lucy1098.assignments.website.util.Closeables;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteAccountServlet", urlPatterns = {"/deleteAccount"})
public class DeleteAccountServlet extends HttpServlet {

    private ControllerOperations operations = null;

    public void init() throws ServletException {
        operations = ControllerOperations.createDefault();
    }

    public void destroy() {
        Closeables.closeSilently(operations);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        operations.run(request, response, (database, auth)-> {
            try {
                RequestParams params = new RequestParams(request);
                long accountId = params.getLong("accountId");

                Account account = database.getAccounts().selectById(accountId);
                auth.checkAccountAccess(account);

                database.getAccounts().delete(account);

                return new RedirectResult("home");
            } catch (ModelNotFoundException e) {
                throw new PermissionException("no access to account");
            }
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }
}
