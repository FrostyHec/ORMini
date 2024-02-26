package org.frostyheco.databse.interfaces.insert;

import org.frostyheco.databse.methods.BatchInsertInfo;
import org.frostyheco.exception.OperationException;

import java.sql.SQLException;
import java.util.List;

public interface BatchInsert extends InsertHandler {
    <T> long batchInsert(List<T> list, BatchInsertInfo<T> info) throws SQLException, OperationException;

    <T> long batchInsert(List<T> object, int start, int end, BatchInsertInfo<T> info) throws OperationException, SQLException;
    }
