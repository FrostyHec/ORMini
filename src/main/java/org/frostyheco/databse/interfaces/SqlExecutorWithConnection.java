package org.frostyheco.databse.interfaces;

import org.frostyheco.databse.utils.QueryResultResources;
import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.OperationException;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlExecutorWithConnection {
    Connection getConnection() throws SQLException;
    int insert(Connection con, String commandName, Object... args) throws OperationException, SQLException;

    int update(Connection con, String commandName, Object... args) throws OperationException, SQLException;

    int delete(Connection con, String commandName, Object... args) throws OperationException, SQLException;

    void statement(Connection con, String commandName, Object... args) throws OperationException, SQLException;

    Object query(Connection con, String commandName,Object... args) throws OperationException, SQLException, BuildingException;

    void executeVoidSQL(Connection con, String sqls, Object... args) throws SQLException;

    int executeDMLSQL(Connection con, String sqls, Object... args) throws SQLException;

    QueryResultResources executeQuerySQL(Connection con, String sql, Object... args) throws SQLException;
}
