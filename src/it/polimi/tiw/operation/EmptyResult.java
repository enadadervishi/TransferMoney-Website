package it.polimi.tiw.operation;

import it.polimi.tiw.auth.AuthManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmptyResult implements Result {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, AuthManager auth)
            throws ServletException, IOException {

    }
}
