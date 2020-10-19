package it.polimi.tiw.operation;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.tiw.dao.Database;
import it.polimi.tiw.models.Account;
import it.polimi.tiw.models.Transfer;

public class RenderAccountPageResult extends RenderPageResult {

    public RenderAccountPageResult(Database database, Account account, Map<String, Object> attributes) throws SQLException {
        super("/account.jsp", attributes);

        attributes.put("accountId", account.getId());
        attributes.put("accountBalance", account.getBalance());

        List<Transfer> transfers = database.getTransfers().selectByAccountIdDescending(account.getId());
        attributes.put("transfers", transfers);
    }

    public RenderAccountPageResult(Database database, Account account) throws SQLException {
        this(database, account, new HashMap<>());
    }
}
