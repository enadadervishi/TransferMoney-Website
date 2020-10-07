package com.lucy1098.assignments.website.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Transfer implements Model {

    private long id;
    private Timestamp timestamp;
    private double amount;

    private long sourceAccountId;
    private long destinationAccountId;

    public Transfer() {
    }

    public Transfer(ResultSet resultSet) throws SQLException {
        id = resultSet.getLong("id");
        timestamp = resultSet.getTimestamp("timestamp");
        amount = resultSet.getDouble("amount");
        sourceAccountId = resultSet.getLong("sourceAccountId");
        destinationAccountId = resultSet.getLong("destinationAccountId");
    }

    @Override
    public String[] getFieldDescriptors() {
        return new String[] {"id INTEGER AUTO_INCREMENT PRIMARY KEY", "timestamp TIMESTAMP NOT NULL",
                "amount DOUBLE NOT NULL", "sourceAccountId INTEGER NOT NULL",
                "destinationAccountId INTEGER NOT NULL"};
    }

    @Override
    public String[] getFieldNames() {
        return new String[] {"timestamp", "amount", "sourceAccountId", "destinationAccountId"};
    }

    @Override
    public Object[] getFieldValues() {
        return new Object[] {timestamp, amount,
            sourceAccountId, destinationAccountId};
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getDateTimeString() {
        return timestamp.toLocalDateTime().toString();
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public long getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }
}
