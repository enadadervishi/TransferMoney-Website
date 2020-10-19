package it.polimi.tiw.operation;

import it.polimi.tiw.auth.AuthManager;
import it.polimi.tiw.dao.Database;

@FunctionalInterface
public interface Operation {

    Result run(Database database, AuthManager auth) throws Exception;
}
