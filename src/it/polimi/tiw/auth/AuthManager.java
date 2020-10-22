package it.polimi.tiw.auth;

import it.polimi.tiw.exceptions.PermissionException;
import it.polimi.tiw.models.Account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.OptionalLong;

public class AuthManager {

    private static final String CONNECTED_KEY = "loggedUserId";

    private final HttpSession session;

    public AuthManager(HttpSession session) {
        this.session = session;
    }

    public static AuthManager fromRequest(HttpServletRequest request) {
        return new AuthManager(request.getSession(true));
    }

    public OptionalLong getConnectedUserId() {
        Object value = session.getAttribute(CONNECTED_KEY);
        return value == null ? OptionalLong.empty() : OptionalLong.of((long) value);
    }

    public void setConnectedUserId(long id) {
        session.setAttribute(CONNECTED_KEY, id);
    }

    public void logout() {
        session.setAttribute(CONNECTED_KEY, null);
    }

    public void checkAccountAccess(Account account) throws PermissionException {
        OptionalLong optionalLong = getConnectedUserId();
        if (!optionalLong.isPresent() || account.getUserId() != optionalLong.getAsLong()) {
            throw new PermissionException("No access to account");
        }
    }
}
