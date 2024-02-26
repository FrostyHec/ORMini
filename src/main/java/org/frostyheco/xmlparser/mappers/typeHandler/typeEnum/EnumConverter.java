package org.frostyheco.xmlparser.mappers.typeHandler.typeEnum;

import org.frostyheco.exception.InvalidException;

public interface EnumConverter {

    Object getObject(Class<?> fieldType,boolean nullable, Object val) throws InvalidException;
}
