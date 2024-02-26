package org.frostyheco.databse.interfaces;

import org.frostyheco.databse.methods.TransactionInfo;
import org.frostyheco.exception.OperationException;

import java.sql.SQLException;

public interface TransactionExtended {
    Object beginTransaction(TransactionInfo step) throws OperationException, SQLException;
}
