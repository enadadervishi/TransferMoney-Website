package com.lucy1098.assignments.website.controllers;

import com.lucy1098.assignments.website.operation.ControllerOperations;
import com.lucy1098.assignments.website.operation.OperationFlag;
import com.lucy1098.assignments.website.operation.RedirectResult;
import com.lucy1098.assignments.website.models.Account;
import com.lucy1098.assignments.website.util.Closeables;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NewAccountServlet", urlPatterns = {"/newAccount"})
public class NewAccountServlet extends HttpServlet {

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
            long userId = auth.getConnectedUserId().getAsLong();

            Account account = new Account();
            account.setBalance(50.0);
            account.setUserId(userId);

            database.getAccounts().add(account);

            return new RedirectResult("home");
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }
}
