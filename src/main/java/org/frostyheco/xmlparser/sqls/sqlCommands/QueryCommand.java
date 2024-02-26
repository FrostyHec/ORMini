package org.frostyheco.xmlparser.sqls.sqlCommands;

import org.frostyheco.xmlparser.sqls.SQLCommand;

public interface QueryCommand extends SQLCommand, ResultAnalyzable {
    String[] getSQLs();
}
