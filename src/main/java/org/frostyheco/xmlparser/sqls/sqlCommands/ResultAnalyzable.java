package org.frostyheco.xmlparser.sqls.sqlCommands;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.OperationException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultAnalyzable {
    Object analyze(ResultSet[] resultSets) throws OperationException, BuildingException, SQLException;
}
