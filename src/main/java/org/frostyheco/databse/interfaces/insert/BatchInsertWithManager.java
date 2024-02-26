package org.frostyheco.databse.interfaces.insert;

import org.frostyheco.databse.utils.BatchManager;

import java.sql.SQLException;

public interface BatchInsertWithManager extends InsertHandler {
    void insert(BatchManager batch, Object... args) throws SQLException;
}
