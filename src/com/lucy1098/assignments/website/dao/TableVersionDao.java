package com.lucy1098.assignments.website.dao;

import com.lucy1098.assignments.website.models.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableVersionDao {

    private static final String TABLE_NAME = "versions";

    private final Connection connection;

    public TableVersionDao(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        String query = new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME)
                .append(" (tableName VARCHAR(255) NOT NULL PRIMARY KEY, versionInt INTEGER NOT NULL)")
                .toString();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    public void dropTable() throws SQLException {
        String query = new StringBuilder()
                .append("DROP TABLE IF EXISTS ").append(TABLE_NAME)
                .toString();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    public long getVersion(Table table) throws SQLException {
        String query = new StringBuilder()
                .append("SELECT versionInt FROM ").append(TABLE_NAME)
                .append(" WHERE tableName=?")
                .toString();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, table.tableName());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    return -1;
                } else {
                    resultSet.next();
                    return resultSet.getLong("versionInt");
                }
            }
        }
    }

    public void updateVersion(Table table) throws SQLException {
        String query = new StringBuilder()
                .append("REPLACE INTO ").append(TABLE_NAME)
                .append(" (tableName, versionInt) VALUES (?, ?)")
                .toString();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, table.tableName());
            statement.setLong(2, table.currentVersion());

            statement.executeUpdate();
        }
    }
}
