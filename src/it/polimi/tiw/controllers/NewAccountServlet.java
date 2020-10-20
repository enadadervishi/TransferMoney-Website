package it.polimi.tiw.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.models.Account;
import it.polimi.tiw.operation.ControllerOperations;
import it.polimi.tiw.operation.OperationFlag;
import it.polimi.tiw.operation.RedirectResult;
import it.polimi.tiw.util.Closeables;

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
            //My choice
            account.setBalance(50.0);
            account.setUserId(userId);

            database.getAccounts().add(account);

            return new RedirectResult("home");
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }
}
