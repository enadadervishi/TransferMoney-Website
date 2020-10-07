package com.lucy1098.assignments.website.exceptions;

import javax.servlet.http.HttpServletResponse;

public class PermissionException extends HandleException {

    public PermissionException(String message) {
        super(HttpServletResponse.SC_FORBIDDEN, message);
    }
}
