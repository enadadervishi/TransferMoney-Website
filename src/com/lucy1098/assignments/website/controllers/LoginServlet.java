package com.lucy1098.assignments.website.controllers;

import com.lucy1098.assignments.website.exceptions.ModelNotFoundException;
import com.lucy1098.assignments.website.exceptions.OperationException;
import com.lucy1098.assignments.website.operation.ControllerOperations;
import com.lucy1098.assignments.website.operation.RedirectResult;
import com.lucy1098.assignments.website.operation.RenderPageResult;
import com.lucy1098.assignments.website.operation.RequestParams;
import com.lucy1098.assignments.website.operation.login.LoginData;
import com.lucy1098.assignments.website.operation.login.LoginOperation;
import com.lucy1098.assignments.website.util.Closeables;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

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
            if (auth.getConnectedUserId().isPresent()) {
                return new RedirectResult("home");
            } else {
                return new RenderPageResult("/login.jsp");
            }
        });
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        operations.run(request, response, (database, auth)-> {
            try {
                LoginData data = LoginData.fromRequest(new RequestParams(request));
                LoginOperation operation = new LoginOperation(database, auth);
                operation.perform(data);

                return new RedirectResult("home");
            } catch (ModelNotFoundException e) {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("loginError", "User not found for given credentials");

                return new RenderPageResult("/login.jsp", attributes);
            } catch (OperationException e) {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("loginError", e.getMessage());

                return new RenderPageResult("/login.jsp", attributes);
            }
        });
    }
}
