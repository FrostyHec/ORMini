package org.frostyheco.xmlparser.mappers.fieldMappers.nonObject;

import org.frostyheco.exception.InternalException;
import org.frostyheco.exception.InvalidException;

public enum NonObjectType {
    //测试发现，Integer.cast(int)是可以的，但是int.cast(Integer)是不行的，因此基本类型都转型成基本数据类型而非其包装类
    Int(int.class, "int"),
    WrappedInteger(Integer.class, "integer", "WrappedInteger", "java.lang.Integer"),

    Long(long.class, "long"),
    WrappedLong(Long.class, "WrappedLong", "java.lang.Long"),

    Short(short.class, "short"),
    WrappedShort(Short.class, "WrappedShort", "java.lang.Short"),

    Byte(byte.class, "byte"),
    WrappedByte(Byte.class, "WrappedByte", "java.lang.Byte"),

    Float(float.class, "float"),
    WrappedFloat(Float.class, "WrappedFloat", "java.lang.Float"),

    Double(double.class, "double"),
    WrappedDouble(Double.class, "WrappedDouble", "java.lang.Double"),

    Boolean(boolean.class, "boolean","bool"),
    WrappedBoolean(Boolean.class, "WrappedBoolean", "java.lang.Boolean"),

    Char(char.class, "char"),
    WrappedChar(Character.class, "character", "WrappedChar", "java.lang.Character"),

    String(String.class, "string", "java.lang.String"),

    Timestamp(java.sql.Timestamp.class, "timestamp", "java.sql.Timestamp"),

    Date(java.sql.Date.class, "date", "java.sql.Date"),

    Time(java.sql.Time.class, "time", "java.sql.Time"),

    ;
    public final Class<?> pojo;
    public final String[] alias;

    NonObjectType(Class<?> pojo, String... alias) {
        this.pojo = pojo;
        this.alias = alias;
    }

    public static NonObjectType getType(String name) {
        try {
            return getType(name, true);
        } catch (InvalidException e) {
            throw new InternalException(e);
        }
    }

    public static NonObjectType getType(String name, boolean nullIfNotFind) throws InvalidException {
        for (var e : NonObjectType.values()) {
            for (var s : e.alias) {
                if (s.equalsIgnoreCase(name)) return e;
            }
        }
        if (nullIfNotFind) return null;
        else throw new InvalidException("Unable to find non object type: name:" + name);
    }
}
