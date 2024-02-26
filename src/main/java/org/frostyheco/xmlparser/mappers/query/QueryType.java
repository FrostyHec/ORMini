package org.frostyheco.xmlparser.mappers.query;

import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.mappers.query.Impl.MapperParser;
import org.frostyheco.xmlparser.mappers.query.Impl.SingleQueryParser;

public enum QueryType {
    Query("query", new SingleQueryParser()),
    Mapper("mapper", new MapperParser())
    ;
    public final QueryParsable parser;
    public final String name;

    QueryType(String name, QueryParsable parser) {
        this.parser = parser;
        this.name = name;
    }
    public static QueryType getType(String name) throws InvalidException {
        if(name==null||name.equals(""))
            throw new InvalidException("query type is null or empty! name:" + name);
        for (var v: QueryType.values()) {
            if(v.name.equals(name)) return v;
        }
        throw new InvalidException("query type not found! name:"+name);
    }
}
