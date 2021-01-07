package it.polimi.tiw.dao;

import it.polimi.tiw.util.ThrowingRunnable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Database implements AutoCloseable {

    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    private final Connection connection;
    private final UserDao users;
    private final AccountDao accounts;
    private final TransferDao transfers;
    private final AddressBookDao addressBook;

    public Database(Connection connection) throws SQLException {
        this.connection = connection;
        users = new UserDao(connection);
        accounts = new AccountDao(connection);
        transfers = new TransferDao(connection);
        addressBook = new AddressBookDao(connection);

        //Solo un solo check
        if (initialized.compareAndSet(false, true)) {
        	users.createTable();
        	accounts.createTable();
        	transfers.createTable();
        	addressBook.createTable();
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

    public AddressBookDao getAddressBook() {
        return addressBook;
    }

    //Make sure it's an atomic operation
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
