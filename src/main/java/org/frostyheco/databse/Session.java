package org.frostyheco.databse;

import org.frostyheco.databse.interfaces.*;
import org.frostyheco.databse.interfaces.insert.BatchInsert;
import org.frostyheco.databse.interfaces.insert.BatchInsertWithManager;
import org.frostyheco.databse.interfaces.insert.CopyInsert;
import org.frostyheco.exception.OperationException;
import org.frostyheco.xmlparser.sqls.StatementType;

import java.util.Map;

public interface Session extends SqlExecutor, SqlExecutorWithConnection,
        SqlGetter, TransactionExtended, TransactionWithConnection,
        BatchInsert, BatchInsertWithManager, CopyInsert {
    @Deprecated(since = "ORMini v0.0.1")
    void addSQL(String name, String sql, StatementType type) throws OperationException;

    @Deprecated(since = "ORMini v0.0.1")
    void addSQLs(Map<String, String> nameSQL, StatementType type) throws OperationException;

    //Methods to execute sql command by extended interfaces

    //Methods to get sql command by extended interfaces

    //Methods to begin transaction by extended interfaces

}
