package org.frostyheco.databse.interfaces;

import org.frostyheco.exception.OperationException;

public interface SqlGetter {
    String getInsert(String commandName) throws OperationException;
    String getUpdate(String commandName) throws OperationException;
    String getDelete(String commandName) throws OperationException;
    String getStatement(String commandName) throws OperationException;
    String getSegment(String commandName) throws OperationException;
    String getQuery(String commandName) throws OperationException;

}
