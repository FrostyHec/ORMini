package org.frostyheco.xmlparser;

import org.frostyheco.xmlparser.sqls.SQLCommand;
import org.frostyheco.xmlparser.sqls.StatementType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {
    public Map<String, SQLCommand> segments;
    public Map<String, SQLCommand> statements;
    public Map<String, SQLCommand> inserts;
    public Map<String, SQLCommand> updates;
    public Map<String, SQLCommand> deletes;
    public Map<String, SQLCommand> queries;
    public Result(){
        defaultInit();
    }
    public void defaultInit(){
        segments=new HashMap<>();
        statements=new HashMap<>();
        inserts=new HashMap<>();
        updates=new HashMap<>();
        deletes=new HashMap<>();
        queries=new HashMap<>();
    }
    public Map<String,SQLCommand> getByType(StatementType type) {
        return switch (type) {
            case Segment -> segments;
            case Statement -> statements;
            case Insert -> inserts;
            case Update -> updates;
            case Delete -> deletes;
            case Query -> queries;
        };
    }
}
