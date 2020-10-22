package it.polimi.tiw.operation.login;

import it.polimi.tiw.auth.AuthManager;
import it.polimi.tiw.dao.Database;
import it.polimi.tiw.exceptions.ModelNotFoundException;
import it.polimi.tiw.exceptions.OperationException;
import it.polimi.tiw.models.User;

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
