package com.lucy1098.assignments.website.operation.login;

import com.lucy1098.assignments.website.exceptions.ParamException;
import com.lucy1098.assignments.website.operation.RequestParams;

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
