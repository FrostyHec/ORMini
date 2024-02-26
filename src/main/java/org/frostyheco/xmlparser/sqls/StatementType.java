package org.frostyheco.xmlparser.sqls;

import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.sqls.parsers.*;

public enum StatementType {
    Segment("segments", "segment", SegmentParser.class),
    Statement("statements", "statement", StatementParser.class),
    Insert("inserts", "insert", InsertParser.class),
    Update("updates", "update", UpdateParser.class),
    Delete("deletes", "delete", DeleteParser.class),
    Query("queries", "query", QueryParser.class);
    public final Class<?> parser;
    public final String name;
    public final String setName;

    StatementType(String setName, String name, Class<?> parser) {
        this.parser = parser;
        this.name = name;
        this.setName = setName;
    }

    public static StatementType getTypeByName(String name) throws InvalidException {
        if(name==null||name.equals(""))
            throw new InvalidException("statement type is null or empty! name:" + name);
        for (var v : StatementType.values()) {
            if (v.name.equals(name)) return v;
        }
        throw new InvalidException("statement type not found! name:" + name);
    }
}
