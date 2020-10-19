package it.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.polimi.tiw.models.Model;
import it.polimi.tiw.util.ThrowingFunction;

public class ModelQueryExecutor {

    private final Connection connection;
    private final String tableName;
    private final SqlObjectAdapter objectAdapter;

    public ModelQueryExecutor(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
        this.objectAdapter = new SqlObjectAdapter();
    }

    public void createTable(Model model) throws SQLException {
        String[] descriptors = model.getFieldDescriptors();
        createTable(descriptors);
    }

    public void createTable(String[] fieldDescriptors) throws SQLException {
    	// CREATE TABLE IF NOT EXISTS tableName (fieldDescriptor,fieldDescriptor)
        StringBuilder stringBuilder = new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS ").append(tableName)
                .append(" (");

        for (int i = 0; i < fieldDescriptors.length; i++) {
            stringBuilder.append(fieldDescriptors[i]);
            if (i < fieldDescriptors.length - 1) {
                stringBuilder.append(',');
            }
        }

        stringBuilder.append(')');

        String query = stringBuilder.toString();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    public void dropTable() throws SQLException {
    	// DROP TABLE IF EXISTS tableName
        String query = new StringBuilder()
                .append("DROP TABLE IF EXISTS ").append(tableName)
                .toString();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    public <T extends Model> T insert(T model) throws SQLException {
    	// INSERT INTO tableName (username, password, email) VALUES (?, ?, ?)
    	// INSERT INTO tableName (username, password, email) VALUES ('test', 'password12', 'test@gmail.com')
        String[] fieldNames = model.getFieldNames();
        Object[] fieldValues = model.getFieldValues();

        StringBuilder stringBuilder = new StringBuilder()
                .append("INSERT INTO ").append(tableName);
        stringBuilder.append("(");
        appendParameterList(stringBuilder, fieldNames);
        stringBuilder.append(") VALUES (");
        appendParameterList(stringBuilder, fieldValues.length);
        stringBuilder.append(")");

        String query = stringBuilder.toString();

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            putParameterList(statement, 1, fieldValues);

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    model.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("no ID obtained.");
                }
            }

            return model;
        }
    }

    public void update(Model model) throws SQLException {
    	// UPDATE tableName SET username=?, password=?, email=? WHERE id=?
    	// UPDATE tableName SET username='test', password='password12', email='mail@mail.mail' WHERE id=5
        String[] fieldNames = model.getFieldNames();
        Object[] fieldValues = model.getFieldValues();

        StringBuilder stringBuilder = new StringBuilder()
                .append("UPDATE ").append(tableName);
        stringBuilder.append(" SET ");
        appendParameterMap(stringBuilder, fieldNames, ",");
        stringBuilder.append(" WHERE id=?");

        String query = stringBuilder.toString();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int index = putParameterList(statement, 1, fieldValues);
            statement.setLong(index, model.getId());

            statement.executeUpdate();
        }
    }

    public void delete(Model model) throws SQLException {
    	// DELETE FROM tableName WHERE id=?
        String query = new StringBuilder()
                .append("DELETE FROM ").append(tableName)
                .append(" WHERE id=?")
                .toString();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, model.getId());

            statement.executeUpdate();
        }
    }

    public <T extends Model> List<T> selectAll(String where, String additionalSettings,
                                               Object[] params,
                                               ThrowingFunction<ResultSet, T, SQLException> factory)
            throws SQLException {
        String query = new StringBuilder()
                .append("SELECT * FROM ").append(tableName)
                .append(" WHERE ").append(where).append(' ')
                .append(additionalSettings)
                .toString();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            putParameterList(statement, 1, params);

            List<T> results = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    T model = factory.apply(resultSet);
                    results.add(model);
                }

                return results;
            }
        }
    }

    public <T extends Model> Optional<T> selectFirst(String where, String additionalSettings,
                                               Object[] params,
                                               ThrowingFunction<ResultSet, T, SQLException> factory)
        throws SQLException {
        List<T> list = selectAll(where, additionalSettings, params, factory);
        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(list.get(0));
    }

    private void appendParameterList(StringBuilder stringBuilder, int count) {
    	// 'testuser', 'testpassword'....
    	// ?, ?, ?, ?
        for (int i = 0; i < count; i++) {
            stringBuilder.append('?');
            if (i < count - 1) {
                stringBuilder.append(',');
            }
        }
    }

    private void appendParameterList(StringBuilder stringBuilder, String[] data) {
    	// username, password, email
        for (int i = 0; i < data.length; i++) {
            stringBuilder.append(data[i]);
            if (i < data.length - 1) {
                stringBuilder.append(',');
            }
        }
    }

    private void appendParameterMap(StringBuilder stringBuilder, String[] keys, String delimiter) {
    	// key = {username, password, email}
    	// delimeter = ,
        for (int i = 0; i < keys.length; i++) {
        	// username=?
            stringBuilder.append(keys[i]).append("=?");
            if (i < keys.length - 1) {
            	// ,
                stringBuilder.append(delimiter);
            }
        }
    }

    private int putParameterList(PreparedStatement statement, int startIndex, Object[]...params) throws SQLException {
        int paramIndex = startIndex;
        for (Object[] array : params) {
            for (Object param : array) {
                objectAdapter.putInStatement(statement, paramIndex++, param);
            }
        }

        return paramIndex;
    }
}
