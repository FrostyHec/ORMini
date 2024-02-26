package org.frostyheco.xmlparser.inlines;

import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.inlines.Impl.IncludeParser;

public enum InlineType {
    Include("include", IncludeParser.class);
    public final Class<?> parser;
    public final String name;

    InlineType(String name, Class<?> parser) {
        this.parser = parser;
        this.name = name;
    }
    public static InlineType getType(String name) throws InvalidException {
        if(name==null||name.equals(""))
            throw new InvalidException("inline type is null or empty! name:" + name);
        for (var v: InlineType.values()) {
            if(v.name.equals(name)) return v;
        }
        throw new InvalidException("Inline type not found! name:"+name);
    }
}
