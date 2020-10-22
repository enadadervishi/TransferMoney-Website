package it.polimi.tiw.operation;

import it.polimi.tiw.exceptions.ParamException;
import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;

public class RequestParams {

    private final HttpServletRequest request;

    public RequestParams(HttpServletRequest request) {
        this.request = request;
    }

    public int count() {
        return request.getParameterMap().size();
    }

    public String get(String name) throws ParamException {
        String value = StringEscapeUtils.escapeJava(request.getParameter(name));
        if (value == null) {
            throw new ParamException(name, "missing, " + request.getParameterMap());
        }

        return value;
    }

    public double getDouble(String name) throws ParamException {
        try {
            return Double.parseDouble(get(name));
        } catch (NumberFormatException e) {
            throw new ParamException(name, "wrong value type");
        }
    }

    public long getLong(String name) throws ParamException {
        try {
            return Long.parseLong(get(name));
        } catch (NumberFormatException e) {
            throw new ParamException(name, "wrong value type");
        }
    }
}
