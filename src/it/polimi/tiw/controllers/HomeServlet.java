package it.polimi.tiw.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.models.Account;
import it.polimi.tiw.operation.ControllerOperations;
import it.polimi.tiw.operation.OperationFlag;
import it.polimi.tiw.operation.RenderPageResult;
import it.polimi.tiw.util.Closeables;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "HomeServlet", urlPatterns = {"/", "/home"})
public class HomeServlet extends HttpServlet {

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
            long userId = auth.getConnectedUserId().getAsLong();
            List<Account> accounts = database.getAccounts().selectByUserId(userId);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("accounts", accounts);

            return new RenderPageResult("/home.jsp", attributes);
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }
}
