package org.frostyheco.databse.interfaces;

import org.frostyheco.databse.utils.QueryResultResources;
import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.OperationException;

import java.sql.SQLException;

public interface SqlExecutor {
    int insert(String commandName, Object... args) throws OperationException, SQLException;

    int update(String commandName, Object... args) throws OperationException, SQLException;

    int delete(String commandName, Object... args) throws OperationException, SQLException;

    void statement(String commandName, Object... args) throws OperationException, SQLException;

    Object query( String commandName,Object... args) throws OperationException, SQLException, BuildingException;

    void executeVoidSQL(String sql, Object... args) throws SQLException;

    int executeDMLSQL(String sql, Object... args) throws SQLException;

    QueryResultResources executeQuerySQL(String sql, Object... args) throws SQLException;
}
