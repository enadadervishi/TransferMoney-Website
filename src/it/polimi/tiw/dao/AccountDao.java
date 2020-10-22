package it.polimi.tiw.dao;

import it.polimi.tiw.models.Account;
import it.polimi.tiw.models.Table;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AccountDao extends ModelDao<Account> {

    public AccountDao(Connection connection) {
        super(Table.ACCOUNT, connection, Account::new);
    }

    public List<Account> selectByUserId(long userId) throws SQLException {
        return executor.selectAll("userId=?", "",
                new Object[] {userId}, Account::new);
    }
}
