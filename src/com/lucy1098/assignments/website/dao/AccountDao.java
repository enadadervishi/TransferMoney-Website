package com.lucy1098.assignments.website.dao;

import com.lucy1098.assignments.website.models.Account;
import com.lucy1098.assignments.website.models.Table;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AccountDao extends ModelDao<Account> {

    public AccountDao(Connection connection) {
        super(Table.ACCOUNT, connection, Account::new);
    }

    public List<Account> selectByUserId(long userId) throws SQLException {
        return executor.selectAll(new String[] {"userId"}, new Object[] {userId},
                Account::new);
    }
}
