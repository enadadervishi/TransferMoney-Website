package com.lucy1098.assignments.website.dao;

import com.lucy1098.assignments.website.models.Table;
import com.lucy1098.assignments.website.util.ThrowingRunnable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Database implements AutoCloseable {

    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    private final Connection connection;
    private final UserDao users;
    private final AccountDao accounts;
    private final TransferDao transfers;

    public Database(Connection connection) throws SQLException {
        this.connection = connection;
        users = new UserDao(connection);
        accounts = new AccountDao(connection);
        transfers = new TransferDao(connection);

        if (initialized.compareAndSet(false, true)) {
            TableVersionDao versionDao = new TableVersionDao(connection);
            versionDao.createTable();

            Map<Table, ModelDao<?>> daos = new HashMap<>();
            daos.put(Table.USER, users);
            daos.put(Table.ACCOUNT, accounts);
            daos.put(Table.TRANSFER, transfers);

            updateTables(versionDao, daos);
        }
    }

    public UserDao getUsers() {
        return users;
    }

    public AccountDao getAccounts() {
        return accounts;
    }

    public TransferDao getTransfers() {
        return transfers;
    }

    public <E extends Exception> void doTransaction(ThrowingRunnable<? extends SQLException, E> runnable)
            throws SQLException, E {
        boolean done = false;
        try {
            this.connection.setAutoCommit(false);
            runnable.run();
            this.connection.commit();
            done = true;
        } finally {
            if (!done) {
                this.connection.rollback();
            }

            this.connection.setAutoCommit(true);
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    private void updateTables(TableVersionDao dao, Map<Table, ModelDao<?>> daos) throws SQLException {
        for (Map.Entry<Table, ModelDao<?>> entry : daos.entrySet()) {
            long dbVersion = dao.getVersion(entry.getKey());
            if (dbVersion != entry.getKey().currentVersion()) {
                entry.getValue().dropTable();
                entry.getValue().createTable();

                dao.updateVersion(entry.getKey());
            }
        }
    }
}
