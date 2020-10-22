package it.polimi.tiw.controllers;

import it.polimi.tiw.operation.ControllerOperations;
import it.polimi.tiw.operation.OperationFlag;
import it.polimi.tiw.operation.RenderPageResult;
import it.polimi.tiw.util.Closeables;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        operations.run(request, response,
                (database, auth)-> new RenderPageResult("/app.jsp"),
                OperationFlag.REQUIRE_LOGGED_IN);
    }
}
