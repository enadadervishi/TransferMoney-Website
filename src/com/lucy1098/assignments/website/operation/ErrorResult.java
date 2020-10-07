package com.lucy1098.assignments.website.operation;

import com.lucy1098.assignments.website.auth.AuthManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorResult implements Result {

    private final int errorCode;
    private final String message;

    public ErrorResult(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, AuthManager auth)
            throws ServletException, IOException {
        response.sendError(errorCode, message);
    }
}
