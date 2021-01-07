package it.polimi.tiw.exceptions;

import javax.servlet.http.HttpServletResponse;

/**
 * Permessi di accesso a pag autorizzate
 */
public class PermissionException extends HandleException {

    public PermissionException(String message) {
        super(HttpServletResponse.SC_FORBIDDEN, message);
    }
}
