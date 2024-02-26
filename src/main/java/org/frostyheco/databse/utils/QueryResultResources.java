package org.frostyheco.databse.utils;

import lombok.Getter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
@Getter
public class QueryResultResources implements AutoCloseable {
    public final Connection connection;
    public final Statement statement;
    public final ResultSet resultSet;

    public QueryResultResources(Connection connection, Statement statement, ResultSet resultSet) {
        this.connection = connection;
        this.statement = statement;
        this.resultSet = resultSet;
    }

    @Override
    public void close() throws SQLException {
        closeExceptConnection();
        connection.close();
    }

    public void closeExceptConnection() throws SQLException {
        statement.close();
        resultSet.close();
    }
}
