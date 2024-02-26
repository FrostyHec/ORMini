package org.frostyheco.databse.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface TransactionWithConnection {
    Connection beginTransaction() throws SQLException;
    void commitTransaction(Connection con) throws SQLException;
    void rollbackTransaction(Connection con) throws SQLException;
}
