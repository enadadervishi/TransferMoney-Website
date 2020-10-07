package com.lucy1098.assignments.website.dao;

import com.lucy1098.assignments.website.exceptions.ModelDuplicateException;
import com.lucy1098.assignments.website.exceptions.ModelNotFoundException;
import com.lucy1098.assignments.website.models.Model;
import com.lucy1098.assignments.website.models.Table;
import com.lucy1098.assignments.website.util.ThrowingFunction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class ModelDao<T extends Model> {

    protected final Table table;
    protected final Connection connection;
    protected final ModelQueryExecutor executor;
    private final ThrowingFunction<ResultSet, T, SQLException> selectFactory;

    public ModelDao(Table table, Connection connection, ThrowingFunction<ResultSet, T, SQLException> selectFactory) {
        this.table = table;
        this.connection = connection;
        this.selectFactory = selectFactory;
        this.executor = new ModelQueryExecutor(connection, table.tableName());
    }

    public void createTable() throws SQLException {
        executor.createTable(table.modelClass());
    }

    public void dropTable() throws SQLException {
        executor.dropTable();
    }

    public T add(T model) throws SQLException, ModelDuplicateException {
        try {
            return executor.insert(model);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new ModelDuplicateException(e);
            }

            throw e;
        }
    }

    public void update(T model) throws SQLException {
        executor.update(model);
    }

    public void delete(T model) throws SQLException {
        executor.delete(model);
    }

    public List<T> selectAll() throws SQLException {
        return executor.selectAll(selectFactory);
    }

    public T selectById(long id) throws SQLException, ModelNotFoundException {
        Optional<T> optional = executor.select(id, selectFactory);
        return optional.orElseThrow(ModelNotFoundException::new);
    }
}
