package com.lucy1098.assignments.website.operation;

import com.lucy1098.assignments.website.auth.AuthManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Result {

    void execute(HttpServletRequest request, HttpServletResponse response, AuthManager auth)
            throws ServletException, IOException;

}
