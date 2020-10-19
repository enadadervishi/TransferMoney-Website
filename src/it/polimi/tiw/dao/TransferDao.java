package it.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import it.polimi.tiw.models.Table;
import it.polimi.tiw.models.Transfer;

public class TransferDao extends ModelDao<Transfer> {

    public TransferDao(Connection connection) {
        super(Table.TRANSFER, connection, Transfer::new);
    }

    public List<Transfer> selectByAccountIdDescending(long accountId) throws SQLException {
    	// WHERE sourceAccountId = accountId OR destinationAccountId = accountId
        return executor.selectAll("sourceAccountId=? OR destinationAccountId=?",
                "ORDER BY timestamp DESC",
                new Object[] {accountId, accountId},
                Transfer::new);
    }
}
