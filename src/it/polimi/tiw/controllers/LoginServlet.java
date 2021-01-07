package it.polimi.tiw.controllers;

import it.polimi.tiw.exceptions.ModelNotFoundException;
import it.polimi.tiw.exceptions.OperationException;
import it.polimi.tiw.operation.ControllerOperations;
import it.polimi.tiw.operation.RedirectResult;
import it.polimi.tiw.operation.RenderPageResult;
import it.polimi.tiw.operation.RequestParams;
import it.polimi.tiw.operation.login.LoginData;
import it.polimi.tiw.operation.login.LoginOperation;
import it.polimi.tiw.util.Closeables;

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
                attributes.put("loginError", "User not found for given credentials"); //jsp $loginError

                return new RenderPageResult("/login.jsp", attributes);
            } catch (OperationException e) {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("loginError", e.getMessage());

                return new RenderPageResult("/login.jsp", attributes);
            }
        });
    }
}
