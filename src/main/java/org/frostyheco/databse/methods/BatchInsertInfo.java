package org.frostyheco.databse.methods;

import org.frostyheco.databse.interfaces.insert.BatchInsertWithManager;
import org.frostyheco.databse.utils.BatchManager;

import java.sql.SQLException;

public abstract class BatchInsertInfo<T> {
    private final String commandName;
    private BatchManager batch;
    private BatchInsertWithManager session;
    private final boolean rewriteBatchInsert;
    private final int batchSize;

    //for session to use
    public final void sessionInitialize(BatchInsertWithManager session, BatchManager batch) {
        this.session = session;
        this.batch = batch;
    }

    public final String getInsertName() {
        return commandName;
    }

    public final int getBatchSize() {
        return batchSize;
    }

    public final boolean isRewriteBatchInsert() {
        return rewriteBatchInsert;
    }

    //for user to use
    protected BatchInsertInfo(String commandName) {
        this(commandName, 2000, true);
    }

    protected BatchInsertInfo(String commandName, int batchSize) {
        this(commandName, batchSize, true);
    }

    protected BatchInsertInfo(String commandName, int batchSize, boolean rewriteBatchInsert) {
        this.commandName = commandName;
        this.rewriteBatchInsert = rewriteBatchInsert;
        this.batchSize = batchSize;
    }

    public abstract void insert(T object) throws SQLException;

    public final void insert(Object... args) throws SQLException {
        session.insert(batch, args);
    }
}
