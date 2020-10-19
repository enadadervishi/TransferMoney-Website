package it.polimi.tiw.operation.registration;

import it.polimi.tiw.exceptions.ParamException;
import it.polimi.tiw.models.User;
import it.polimi.tiw.operation.RequestParams;

public class RegistrationData {

    private final String username;
    private final String email;
    private final String password;
    private final String repeatPassword;

    public RegistrationData(String username, String email, String password, String repeatPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public static RegistrationData fromRequest(RequestParams params) throws ParamException {
        return new RegistrationData(params.get("username"),
                params.get("email"),
                params.get("password"),
                params.get("repeat-password"));
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public User toModel() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        return user;
    }
}
