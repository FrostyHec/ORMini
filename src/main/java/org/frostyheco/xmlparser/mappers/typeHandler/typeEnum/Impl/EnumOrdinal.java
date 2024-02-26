package org.frostyheco.xmlparser.mappers.typeHandler.typeEnum.Impl;

import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.mappers.typeHandler.typeEnum.EnumConverter;
import org.frostyheco.xmlparser.mappers.typeHandler.typeEnum.utils.EnumConvertUtils;

public class EnumOrdinal implements EnumConverter {
    @Override
    public Object getObject(Class<?> fieldType, boolean nullable, Object val) throws InvalidException {
        return EnumConvertUtils.compare(fieldType, val, nullable, (e, v) -> e.ordinal() == (int) val);
    }
}
