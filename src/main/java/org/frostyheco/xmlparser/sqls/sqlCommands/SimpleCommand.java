package org.frostyheco.xmlparser.sqls.sqlCommands;

import org.frostyheco.xmlparser.sqls.SQLCommand;

public class SimpleCommand implements SQLCommand {
    private String sql;
    public SimpleCommand(){

    }
    public SimpleCommand(String s){
        sql=s;
    }
    @Override
    public String getSQL() {
        return sql;
    }

    @Override
    public void setSQL(String sql) {
        this.sql=sql;
    }
}
