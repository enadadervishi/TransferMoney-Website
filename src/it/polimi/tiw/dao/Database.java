package it.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

import it.polimi.tiw.util.ThrowingRunnable;

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

        //Thanks to initialized this clause is entered once
        if (initialized.compareAndSet(false, true)) {
        	users.createTable();
        	accounts.createTable();
        	transfers.createTable();
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

    /**
     * The transaction is done after db autoCommit is stopped
     * If something goes wrong then rollback 
     */
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
}
