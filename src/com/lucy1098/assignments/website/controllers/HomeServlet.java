package com.lucy1098.assignments.website.controllers;

import com.lucy1098.assignments.website.models.Account;
import com.lucy1098.assignments.website.operation.ControllerOperations;
import com.lucy1098.assignments.website.operation.OperationFlag;
import com.lucy1098.assignments.website.operation.RenderPageResult;
import com.lucy1098.assignments.website.util.Closeables;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
