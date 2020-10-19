package it.polimi.tiw.operation.registration;

import java.sql.SQLException;
import java.util.regex.Pattern;

import it.polimi.tiw.auth.AuthManager;
import it.polimi.tiw.dao.Database;
import it.polimi.tiw.exceptions.ModelDuplicateException;
import it.polimi.tiw.exceptions.OperationException;
import it.polimi.tiw.models.User;

public class RegistrationOperation {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");

    private final Database database;
    private final AuthManager auth;

    public RegistrationOperation(Database database, AuthManager auth) {
        this.database = database;
        this.auth = auth;
    }

    public void perform(RegistrationData data) throws OperationException, SQLException, ModelDuplicateException {
        validate(data);

        User user = data.toModel();
        user = database.getUsers().add(user);
        auth.setConnectedUserId(user.getId());
    }

    public void validate(RegistrationData registrationData) throws OperationException {
        if (!hasString(registrationData.getUsername())) {
            throw new OperationException("username missing");
        }

        if (!hasString(registrationData.getEmail())) {
            throw new OperationException("email missing");
        }
        if (!EMAIL_REGEX.matcher(registrationData.getEmail()).find()) {
            throw new OperationException("email does not match requirements");
        }

        if (!hasString(registrationData.getPassword())) {
            throw new OperationException("password missing");
        }

        if (!registrationData.getRepeatPassword().equals(registrationData.getPassword())) {
            throw new OperationException("passwords don't match");
        }
    }

    private boolean hasString(String element) {
        return element != null && !element.trim().isEmpty();
    }
}
