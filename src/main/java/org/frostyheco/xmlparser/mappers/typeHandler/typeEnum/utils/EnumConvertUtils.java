package org.frostyheco.xmlparser.mappers.typeHandler.typeEnum.utils;

import org.frostyheco.exception.InvalidException;

public class EnumConvertUtils {
    public static Object compare(Class<?> fieldType, Object val,boolean canBeNull,EnumComparator enumComparator) throws InvalidException {
        if(val==null){
            if(canBeNull) return null;
            else throw new InvalidException("ObjectVal is null");
        }
        if(val.getClass().equals(fieldType)) return val;//Input is exactly an enum constrain.
        Object[] constrains = fieldType.getEnumConstants();
        for (Object o : constrains) {
            Enum<?> constrain = (Enum<?>) o;
            if (enumComparator.equals(constrain,val)) return constrain;
        }
        throw new InvalidException("Can't find enum type by val, val:" + val);
    }
}
