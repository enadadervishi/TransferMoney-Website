package it.polimi.tiw.exceptions;

import javax.servlet.http.HttpServletResponse;

public class PermissionException extends HandleException {

    public PermissionException(String message) {
        super(HttpServletResponse.SC_FORBIDDEN, message);
    }
}
