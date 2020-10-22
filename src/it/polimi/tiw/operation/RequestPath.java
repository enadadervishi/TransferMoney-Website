package it.polimi.tiw.operation;

import it.polimi.tiw.exceptions.ParamException;
import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class RequestPath {

    private final String[] paths;

    public RequestPath(String path) {
        this.paths = path == null ? new String[0] : path.split("/");
    }

    public RequestPath(HttpServletRequest request) {
        this(request.getPathInfo());
    }

    public int getPathCount() {
        return paths.length;
    }

    public boolean hasParams() {
        return paths.length > 0;
    }

    public String getParam(int index) throws ParamException {
        if (index >= getPathCount()) {
            throw new ParamException(String.valueOf(index), "missing");
        }

        String value = StringEscapeUtils.escapeJava(paths[index]);
        if (value == null) {
            throw new ParamException(String.valueOf(index), "no value");
        }

        return value;
    }

    public long getLongParam(int index) throws ParamException {
        try {
            return Long.parseLong(getParam(index));
        } catch (NumberFormatException e) {
            throw new ParamException(String.valueOf(index), "wrong value type");
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(paths);
    }
}
