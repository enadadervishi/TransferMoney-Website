package com.lucy1098.assignments.website.operation;

import com.lucy1098.assignments.website.dao.Database;
import com.lucy1098.assignments.website.models.Account;
import com.lucy1098.assignments.website.models.Transfer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
