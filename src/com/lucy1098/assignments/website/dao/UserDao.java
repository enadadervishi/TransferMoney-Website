package com.lucy1098.assignments.website.dao;

import com.lucy1098.assignments.website.exceptions.ModelNotFoundException;
import com.lucy1098.assignments.website.models.Table;
import com.lucy1098.assignments.website.models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

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
