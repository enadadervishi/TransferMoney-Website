package com.lucy1098.assignments.website.operation.login;

import com.lucy1098.assignments.website.auth.AuthManager;
import com.lucy1098.assignments.website.dao.Database;
import com.lucy1098.assignments.website.exceptions.ModelNotFoundException;
import com.lucy1098.assignments.website.exceptions.OperationException;
import com.lucy1098.assignments.website.models.User;

import java.sql.SQLException;

public class LoginOperation {

    private final Database database;
    private final AuthManager auth;

    public LoginOperation(Database database, AuthManager auth) {
        this.database = database;
        this.auth = auth;
    }

    public void perform(LoginData data) throws OperationException, ModelNotFoundException, SQLException {
        if (data.getUsername() == null || data.getPassword() == null || data.getUsername().isEmpty() || data.getPassword().isEmpty()) {
            throw new OperationException("username and password data missing");
        }

        User user = database.getUsers().selectByLogin(data.getUsername(), data.getPassword());
        auth.setConnectedUserId(user.getId());
    }
}
