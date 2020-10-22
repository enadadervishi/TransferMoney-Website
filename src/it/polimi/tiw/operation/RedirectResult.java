package it.polimi.tiw.operation;

import it.polimi.tiw.auth.AuthManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectResult implements Result {

    private final String path;

    public RedirectResult(String path) {
        this.path = path;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, AuthManager auth)
            throws ServletException, IOException {
        response.sendRedirect(path);
    }
}
