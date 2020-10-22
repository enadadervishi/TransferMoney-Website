package it.polimi.tiw.operation;

import com.google.gson.Gson;
import it.polimi.tiw.auth.AuthManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonResult implements Result {

    private final Gson gson;
    private final Object value;

    public JsonResult(Gson gson, Object value) {
        this.gson = gson;
        this.value = value;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, AuthManager auth)
            throws ServletException, IOException {
        response.setContentType("application/json");

        String json = gson.toJson(value);

        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.flush();
    }
}
