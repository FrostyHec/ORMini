package org.frostyheco.databse.methods;

import org.frostyheco.databse.SessionFactory;
import org.frostyheco.databse.utils.QueryResultResources;
import org.frostyheco.databse.utils.BatchManager;
import org.frostyheco.exception.BuildingException;
import org.frostyheco.xmlparser.Result;
import org.frostyheco.databse.Session;
import org.frostyheco.xmlparser.XmlParser;
import org.frostyheco.exception.OperationException;
import org.frostyheco.xmlparser.sqls.SQLCommand;
import org.frostyheco.xmlparser.sqls.StatementType;
import org.frostyheco.xmlparser.sqls.sqlCommands.QueryCommand;
import org.frostyheco.xmlparser.sqls.sqlCommands.SimpleCommand;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class BasicOperation implements Session {
    protected DataSource source;

    protected Result sqls;

    public BasicOperation(DataSource s, String name) throws BuildingException {
        source = s;
        sqls = XmlParser.parse(this.getClass().getResourceAsStream(SessionFactory.getSqlPath(name)));
    }

    @Override
    public void addSQL(String name, String sql, StatementType type) throws OperationException {
        var m = sqls.getByType(type);
        if (m.containsKey(name))
            throw new OperationException("sql command exists: name:" + name + " sql:" + m.get(name));
        m.put(name, new SimpleCommand(sql));
    }

    @Override
    public void addSQLs(Map<String, String> nameSQL, StatementType type) throws OperationException {
        for (var e : nameSQL.entrySet()) {
            addSQL(e.getKey(), e.getValue(), type);
        }
    }

    @Override
    public String getInsert(String commandName) throws OperationException {
        return getSQL(sqls.inserts, commandName);
    }

    @Override
    public String getUpdate(String commandName) throws OperationException {
        return getSQL(sqls.updates, commandName);
    }

    @Override
    public String getDelete(String commandName) throws OperationException {
        return getSQL(sqls.deletes, commandName);
    }

    @Override
    public String getStatement(String commandName) throws OperationException {
        return getSQL(sqls.statements, commandName);
    }

    @Override
    public String getSegment(String commandName) throws OperationException {
        return getSQL(sqls.segments, commandName);
    }

    @Override
    public String getQuery(String commandName) throws OperationException {
        return getSQL(sqls.queries, commandName);
    }

    private String getSQL(Map<String, SQLCommand> map, String commandName) throws OperationException {
        SQLCommand cmd = map.get(commandName);
        if (cmd == null) throw new OperationException("name not found! name:" + commandName);
        return cmd.getSQL();
    }

    @Override
    public int insert(String commandName, Object... args) throws OperationException, SQLException {
        try (Connection con = source.getConnection()) {
            return insert(con, commandName, args);
        }
    }


    @Override
    public int update(String commandName, Object... args) throws OperationException, SQLException {
        try (Connection con = source.getConnection()) {
            return update(con, commandName, args);
        }
    }

    @Override
    public int delete(String commandName, Object... args) throws OperationException, SQLException {
        try (Connection con = source.getConnection()) {
            return delete(con, commandName, args);
        }
    }

    @Override
    public int insert(Connection con, String commandName, Object... args) throws OperationException, SQLException {
        String p = getInsert(commandName);
        if (p == null) throw new OperationException("SQL no found, name:" + commandName);
        //log.info("executing insert command, name:" + commandName);
        return executeDMLSQL(con, p, args);
    }

    @Override
    public int update(Connection con, String commandName, Object... args) throws OperationException, SQLException {
        String p = getUpdate(commandName);
        if (p == null) throw new OperationException("SQL no found, name:" + commandName);
        //log.info("executing update command, name:" + commandName);
        return executeDMLSQL(con, p, args);
    }

    @Override
    public int delete(Connection con, String commandName, Object... args) throws OperationException, SQLException {
        String p = getDelete(commandName);
        if (p == null) throw new OperationException("SQL no found, name:" + commandName);
        //log.info("executing delete command, name:" + commandName);
        return executeDMLSQL(con, p, args);
    }

    @Override
    public void statement(String commandName, Object... args) throws OperationException, SQLException {
        try (Connection con = source.getConnection()) {
            statement(con, commandName, args);
        }
    }

    @Override
    public Object query(String commandName, Object... args) throws OperationException, SQLException, BuildingException {
        try (Connection con = source.getConnection()) {
            return query(con, commandName, args);
        }
    }

    @Override
    public Object query(Connection con, String commandName, Object... args) throws OperationException, SQLException, BuildingException {
        QueryCommand cmd = (QueryCommand) sqls.queries.get(commandName);
        if (cmd == null) throw new OperationException("SQL no found, name:" + commandName);
        String[] querySQLs = cmd.getSQLs();
        int len = querySQLs.length;
        QueryResultResources[] rsss = new QueryResultResources[len];
        for (int i = 0; i < len; i++) {
            rsss[i] = executeQuerySQL(con, querySQLs[i], args);
        }
        Object res = cmd.analyze(Arrays.stream(rsss)
                .map(QueryResultResources::getResultSet)
                .toArray(ResultSet[]::new));
        //close a connection for a lot of time won't occur error, connection will be closed if rss is closed.
        for (QueryResultResources v : rsss) v.closeExceptConnection();
        return res;
    }

    @Override
    public void executeVoidSQL(String sql, Object... args) throws SQLException {
        try (Connection con = source.getConnection()) {
            executeVoidSQL(con, sql, args);
        }
    }

    @Override
    public int executeDMLSQL(String sql, Object... args) throws SQLException {
        try (Connection con = source.getConnection()) {
            return executeDMLSQL(con, sql, args);
        }
    }

    @Override
    public QueryResultResources executeQuerySQL(String sql, Object... args) throws SQLException {
        try (Connection con = source.getConnection()) {
            return executeQuerySQL(con, sql, args);
        }
    }
////////////////////////////////////////////////////connection api/////////////////////////////////////////////////////////////////


    @Override
    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    @Override
    public void statement(Connection con, String commandName, Object... args) throws OperationException, SQLException {
        String sql = getStatement(commandName);
        if (sql == null) throw new OperationException("SQL no found, name:" + commandName);
        executeVoidSQL(con, sql, args);
    }

    @Override
    public void executeVoidSQL(Connection con, String sql, Object... args) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            stmt.execute();
        }
    }

    @Override
    public int executeDMLSQL(Connection con, String sql, Object... args) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            return stmt.executeUpdate();
        }
    }

    /**
     * Remember to close the resources!
     */
    @Override
    public QueryResultResources executeQuerySQL(Connection con, String sql, Object... args) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return new QueryResultResources(con, stmt, stmt.executeQuery());
    }

    @Override
    public Object beginTransaction(TransactionInfo step) throws OperationException, SQLException {
        //it will throw a sql exception only when rollback failed, and operation exception when invalid executing parameters.
        return step.executeStep(this);
    }

    @Override
    public Connection beginTransaction() throws SQLException {
        Connection con = source.getConnection();
        con.setAutoCommit(false);
        return con;
    }

    @Override
    public void commitTransaction(Connection con) throws SQLException {
        con.commit();
    }

    @Override
    public void rollbackTransaction(Connection con) throws SQLException {
        con.rollback();
    }

    ////////////////////////////////////////     Batch Insert       ///////////////////////////////////////////////////////
    @Override
    public <T> long batchInsert(List<T> list, BatchInsertInfo<T> info) throws SQLException, OperationException {
        return batchInsert(list, 0, list.size(), info);
    }

    @Override
    public void insert(BatchManager batch, Object... args) throws SQLException {
        int len = args.length;
        for (int i = 0; i < len; i++) {
            batch.setObject(i + 1, args[i]);
        }
        batch.addBatch();
    }

    @Override
    public <T> long batchInsert(List<T> list, int start, int end, BatchInsertInfo<T> info) throws OperationException, SQLException {
        if (start < 0 || end > list.size())
            throw new OperationException("illegal begin/end index on batch insert: start:" + start + "end:" + end);
        try (Connection con = source.getConnection();
             PreparedStatement stmt = con.prepareStatement(getInsert(info.getInsertName()));
             BatchManager batch = new BatchManager(stmt, info.getBatchSize())) {
            try {
                con.setAutoCommit(false);
                //con.getClientInfo().setProperty("reWriteBatchedInserts", String.valueOf(info.isRewriteBatchInsert()));
                info.sessionInitialize(this, batch);
                for (int i = start; i < end; i++) {
                    T t = list.get(i);
                    info.insert(t);
                }
                batch.finalExecute();
                con.commit();
                return batch.getTotalCount();
            } catch (SQLException e) {
                e.printStackTrace();
                con.rollback();
                return -1;
            }
        }
    }

    @Override
    public <T> long copyInsert(List<T> list, CopyInsertInfo<T> info) throws SQLException, OperationException {
        try {
            String table = info.getTableName();
            String path = info.getPathName();
            long cnt = info.printData(list);
            try (Connection con = source.getConnection(); Statement stmt = con.createStatement()) {
                stmt.executeUpdate("copy " + table
                        + " from '" + path + "' with delimiter '" + info.delimiter + "' csv quote '\"'");
            }
            return cnt;
        } catch (IOException e) {
            throw new OperationException("Error occurred in copyInsert!:", e);
        } finally {
            try {
                info.deleteTmpIfExist();
            } catch (IOException e) {
                throw new OperationException(e);
            }
        }
    }
}
