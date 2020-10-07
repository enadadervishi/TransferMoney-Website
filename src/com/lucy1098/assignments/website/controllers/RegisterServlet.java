package com.lucy1098.assignments.website.controllers;

import com.lucy1098.assignments.website.exceptions.ModelDuplicateException;
import com.lucy1098.assignments.website.exceptions.OperationException;
import com.lucy1098.assignments.website.operation.ControllerOperations;
import com.lucy1098.assignments.website.operation.RedirectResult;
import com.lucy1098.assignments.website.operation.RenderPageResult;
import com.lucy1098.assignments.website.operation.RequestParams;
import com.lucy1098.assignments.website.operation.registration.RegistrationData;
import com.lucy1098.assignments.website.operation.registration.RegistrationOperation;
import com.lucy1098.assignments.website.util.Closeables;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

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
                return new RenderPageResult("/register.jsp");
            }
        });
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        operations.run(request, response, (database, auth)-> {
            try {
                RegistrationData data = RegistrationData.fromRequest(new RequestParams(request));
                RegistrationOperation operation = new RegistrationOperation(database, auth);

                operation.perform(data);

                return new RedirectResult("home");
            } catch (OperationException e) {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("registerError", e.getMessage());

                return new RenderPageResult("/register.jsp", attributes);
            } catch (ModelDuplicateException e) {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("registerError", "Username taken");

                return new RenderPageResult("/register.jsp", attributes);
            }
        });
    }
}
