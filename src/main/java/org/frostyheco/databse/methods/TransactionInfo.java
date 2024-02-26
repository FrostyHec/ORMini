package org.frostyheco.databse.methods;

import org.frostyheco.databse.Session;
import org.frostyheco.databse.utils.QueryResultResources;
import org.frostyheco.databse.interfaces.SqlExecutor;
import org.frostyheco.databse.interfaces.SqlGetter;
import org.frostyheco.databse.interfaces.TransactionExtended;
import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.OperationException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class TransactionInfo implements SqlExecutor, SqlGetter, TransactionExtended {
    private Session session;
    private Connection con;

    public Object executeStep(Session session) throws OperationException, SQLException {
        this.session = session;
        con = session.beginTransaction();
        Object o = null;
        try {
            o = inTransaction();
            session.commitTransaction(con);
        } catch (OperationException e) {
            throw new OperationException(e);
        } catch (SQLException e) {
            onSQLException(e);//If rollback failed, it will throw a sql exception.
        } finally {
            con.close();
        }
        return o;
    }

    public abstract Object inTransaction() throws OperationException, SQLException;//user required to implement this method

    public void onSQLException(SQLException e) throws SQLException {//overridable
        e.printStackTrace();
        session.rollbackTransaction(con);
    }

    @Override
    public final int insert(String commandName, Object... args) throws OperationException, SQLException {
        return session.insert(con, commandName, args);
    }

    @Override
    public final int update(String commandName, Object... args) throws OperationException, SQLException {
        return session.update(con, commandName, args);
    }

    @Override
    public final int delete(String commandName, Object... args) throws OperationException, SQLException {
        return session.delete(con, commandName, args);
    }

    @Override
    public final void statement(String commandName, Object... args) throws OperationException, SQLException {
        session.statement(con, commandName, args);
    }

    @Override
    public final Object query(String commandName,Object... args) throws OperationException, SQLException, BuildingException {
        return session.query(con, commandName,args);
    }

    @Override
    public final void executeVoidSQL(String sql, Object... args) throws SQLException {
        session.executeVoidSQL(con, sql, args);
    }

    @Override
    public final int executeDMLSQL(String sql, Object... args) throws SQLException {
        return session.executeDMLSQL(con, sql, args);
    }
    /**
     * Remember to close the resources!
     */
    public final QueryResultResources executeQuerySQL(String sql, Object... args) throws SQLException {
        return session.executeQuerySQL(con, sql, args);
    }

    @Override
    public final String getInsert(String commandName) throws OperationException {
        return session.getInsert(commandName);
    }

    @Override
    public final String getUpdate(String commandName) throws OperationException {
        return session.getUpdate(commandName);
    }

    @Override
    public final String getDelete(String commandName) throws OperationException {
        return session.getDelete(commandName);
    }

    @Override
    public final String getStatement(String commandName) throws OperationException {
        return session.getStatement(commandName);
    }

    @Override
    public final String getSegment(String commandName) throws OperationException {
        return session.getSegment(commandName);
    }

    @Override
    public final String getQuery(String commandName) throws OperationException {
        return session.getQuery(commandName);
    }

    @Override
    public final Object beginTransaction(TransactionInfo step) throws OperationException, SQLException {
        return session.beginTransaction(step);
    }
}
