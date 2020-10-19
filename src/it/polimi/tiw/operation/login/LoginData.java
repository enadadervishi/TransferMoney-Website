package it.polimi.tiw.operation.login;

import it.polimi.tiw.exceptions.ParamException;
import it.polimi.tiw.operation.RequestParams;

public class LoginData {

    private final String username;
    private final String password;

    public LoginData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static LoginData fromRequest(RequestParams params) throws ParamException {
        return new LoginData(
                params.get("username"),
                params.get("password")
        );
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
