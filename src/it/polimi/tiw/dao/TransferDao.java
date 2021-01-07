package it.polimi.tiw.dao;

import it.polimi.tiw.models.Table;
import it.polimi.tiw.models.Transfer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TransferDao extends ModelDao<Transfer> {

    public TransferDao(Connection connection) {
        super(Table.TRANSFER, connection, Transfer::new);
    }

    public List<Transfer> selectByAccountIdDescending(long accountId) throws SQLException {
    	// WHERE sourceAccountId = accountId OR destinationAccountId = accountId
    	//dipende da chi è la source/destination
        return executor.selectAll("sourceAccountId=? OR destinationAccountId=?",
                "ORDER BY timestamp DESC",
                new Object[] {accountId, accountId}, Transfer::new);
    }
}
