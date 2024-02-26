package org.frostyheco.databse.interfaces.insert;

import org.frostyheco.databse.methods.CopyInsertInfo;
import org.frostyheco.exception.OperationException;

import java.sql.SQLException;
import java.util.List;

public interface CopyInsert extends InsertHandler {
    <T> long copyInsert(List<T> list, CopyInsertInfo<T> info) throws SQLException, OperationException;

}
