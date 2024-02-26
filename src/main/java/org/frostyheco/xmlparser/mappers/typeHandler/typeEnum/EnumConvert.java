package org.frostyheco.xmlparser.mappers.typeHandler.typeEnum;

import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.mappers.typeHandler.typeEnum.Impl.EnumName;
import org.frostyheco.xmlparser.mappers.typeHandler.typeEnum.Impl.EnumOrdinal;
import org.frostyheco.xmlparser.mappers.typeHandler.typeEnum.Impl.Enum_1_Ordinal;

public enum EnumConvert {
    Name("name", new EnumName()),
    Ordinal("ordinal",new EnumOrdinal()),
    OrdinalBeginIn1("one begin",new Enum_1_Ordinal());
    private final String name;
    private final EnumConverter impl;

    EnumConvert(String name, EnumConverter impl) {
        this.name = name;
        this.impl = impl;
    }

    public EnumConverter getImpl() {
        return impl;
    }
    public static EnumConvert getType(String name) throws InvalidException {
        if(name==null||name.equals(""))
            throw new InvalidException("enum convert type is null or empty! name:" + name);
        for (var v: EnumConvert.values()) {
            if(v.name.equals(name)) return v;
        }
        throw new InvalidException("enum convert not found! name:"+name);
    }
}
