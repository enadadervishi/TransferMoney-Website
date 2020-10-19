package it.polimi.tiw.operation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.auth.AuthManager;

import java.io.IOException;

public interface Result {

    void execute(HttpServletRequest request, HttpServletResponse response, AuthManager auth)
            throws ServletException, IOException;

}
