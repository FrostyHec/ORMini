package org.frostyheco.xmlparser.mappers.fieldMappers.nonObject;

import org.frostyheco.xmlparser.sqls.sqlCommands.SingleQueryCommand;

import java.sql.ResultSet;

/**
 * helper for nonObject since initializer is reduntant but required in
 * @see SingleQueryCommand#analyze(ResultSet[])
 */
public class DummyNonObject {
    public DummyNonObject(){}
}
