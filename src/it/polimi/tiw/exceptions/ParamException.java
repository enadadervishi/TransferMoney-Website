package it.polimi.tiw.exceptions;

import javax.servlet.http.HttpServletResponse;

public class ParamException extends HandleException {

    public ParamException(String param, String message) {
        super(HttpServletResponse.SC_BAD_REQUEST, param + ": " + message);
    }
}
