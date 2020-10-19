package it.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import it.polimi.tiw.exceptions.ModelNotFoundException;
import it.polimi.tiw.models.Table;
import it.polimi.tiw.models.User;

public class UserDao extends ModelDao<User> {

    public UserDao(Connection connection) {
        super(Table.USER, connection, User::new);
    }

    public User selectByLogin(String username, String password) throws SQLException, ModelNotFoundException {
        Optional<User> optional = executor.selectFirst(new String[] {"username", "password"},
                new Object[] {username, password},
                User::new);
        return optional.orElseThrow(ModelNotFoundException::new);
    }
}
