package com.lucy1098.assignments.website.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SqlObjectAdapter {

    public void putInStatement(PreparedStatement statement, int index, Object object) throws SQLException {
        if (object instanceof Date) {
            statement.setDate(index, (Date) object);
        } else if (object instanceof Timestamp) {
            statement.setTimestamp(index, (Timestamp) object);
        } else if (object instanceof String) {
            statement.setString(index, (String) object);
        } else if (object instanceof Float) {
            statement.setDouble(index, (float) object);
        } else if (object instanceof Double) {
            statement.setDouble(index, (double) object);
        } else if (object instanceof Byte) {
            statement.setShort(index, (byte) object);
        } else if (object instanceof Short) {
            statement.setShort(index, (short) object);
        } else if (object instanceof Integer) {
            statement.setInt(index, (int) object);
        } else if (object instanceof Long) {
            statement.setLong(index, (long) object);
        } else if (object instanceof Boolean) {
            statement.setBoolean(index, (boolean) object);
        } else {
            throw new SQLException("unknown type: " + object.getClass().getName());
        }
    }
}
