package it.polimi.tiw.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressBook implements Model {

    private long id;
    private long userId;

    private long recipientUserId;
    private long recipientAccountId;

    public AddressBook() {
    }

    public AddressBook(ResultSet resultSet) throws SQLException {
        id = resultSet.getLong("id");
        userId = resultSet.getLong("userId");
        recipientUserId = resultSet.getLong("recipientUserId");
        recipientAccountId = resultSet.getLong("recipientAccountId");
    }

    @Override
    public String[] getFieldDescriptors() {
        return new String[] {"id INTEGER AUTO_INCREMENT PRIMARY KEY", "userId INTEGER NOT NULL",
                "recipientUserId INTEGER NOT NULL", "recipientAccountId INTEGER NOT NULL"};
    }

    @Override
    public String[] getFieldNames() {
        return new String[] {"userId", "recipientUserId", "recipientAccountId"};
    }

    @Override
    public Object[] getFieldValues() {
        return new Object[] {userId, recipientUserId, recipientAccountId};
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRecipientUserId() {
        return recipientUserId;
    }

    public void setRecipientUserId(long recipientUserId) {
        this.recipientUserId = recipientUserId;
    }

    public long getRecipientAccountId() {
        return recipientAccountId;
    }

    public void setRecipientAccountId(long recipientAccountId) {
        this.recipientAccountId = recipientAccountId;
    }
}
