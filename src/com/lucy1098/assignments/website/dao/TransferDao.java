package com.lucy1098.assignments.website.dao;

import com.lucy1098.assignments.website.models.Table;
import com.lucy1098.assignments.website.models.Transfer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TransferDao extends ModelDao<Transfer> {

    public TransferDao(Connection connection) {
        super(Table.TRANSFER, connection, Transfer::new);
    }

    public List<Transfer> selectByAccountIdDescending(long accountId) throws SQLException {
    	// WHERE sourceAccountId = accountId OR destinationAccountId = accountId
        return executor.selectAll(new String[] {"sourceAccountId", "destinationAccountId"},
                new Object[] {accountId, accountId},
                " OR ", "ORDER BY timestamp DESC", Transfer::new);
    }
}
