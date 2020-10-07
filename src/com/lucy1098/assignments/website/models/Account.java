package com.lucy1098.assignments.website.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Account implements Model {

    private long id;
    private double balance;

    private long userId;

    public Account() {
    }

    public Account(ResultSet resultSet) throws SQLException {
        id = resultSet.getLong("id");
        balance = resultSet.getDouble("balance");
        userId = resultSet.getLong("userId");
    }

    @Override
    public String[] getFieldDescriptors() {
        return new String[] {"id INTEGER AUTO_INCREMENT PRIMARY KEY", "balance DOUBLE", "userId INTEGER NOT NULL"};
    }

    @Override
    public String[] getFieldNames() {
        return new String[] {"balance", "userId"};
    }

    @Override
    public Object[] getFieldValues() {
        return new Object[] {balance, userId};
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
