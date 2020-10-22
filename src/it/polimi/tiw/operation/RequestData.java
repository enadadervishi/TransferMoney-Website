package it.polimi.tiw.operation;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import it.polimi.tiw.exceptions.HandleException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class RequestData {

    public static <T> T parseInto(Gson gson, HttpServletRequest request, Class<T> type) throws IOException, HandleException {
        try {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String payload = buffer.toString();
            return gson.fromJson(payload, type);
        } catch (JsonParseException e) {
            throw new HandleException(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
