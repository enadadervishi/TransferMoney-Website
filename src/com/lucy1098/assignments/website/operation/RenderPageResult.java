package com.lucy1098.assignments.website.operation;

import com.lucy1098.assignments.website.auth.AuthManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.OptionalLong;

public class RenderPageResult implements Result {

    private final String path;
    private final Map<String, Object> attributes;

    public RenderPageResult(String path, Map<String, Object> attributes) {
        this.path = path;
        this.attributes = attributes;
    }

    public RenderPageResult(String path) {
        this(path, Collections.emptyMap());
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, AuthManager auth)
            throws ServletException, IOException {
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }

        OptionalLong optionalLong = auth.getConnectedUserId();
        if (optionalLong.isPresent()) {
            request.setAttribute("loggedUser", optionalLong.getAsLong());
        }

        request.getRequestDispatcher(path).forward(request, response);
    }
}
