package org.frostyheco.xmlparser.mappers.fieldMappers.Impl;

import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.mappers.fieldMappers.FieldMapper;
import org.frostyheco.xmlparser.mappers.mappingUtils.FieldMapUtils;
import org.frostyheco.xmlparser.mappers.mappingUtils.MapInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.Impl.NameMapArgs;
import org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.MapArgs;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class DirectNameMapper implements FieldMapper {
    public static final DirectNameMapper impl = new DirectNameMapper();

    private DirectNameMapper() {
    }

    @Override
    public Object mappingField(Object obj, ReflectInfo reflectInfo, MapInfo mapInfo, ResultSet resultSet, MapArgs[] args) throws InvalidException {
        try {
            var map = ((NameMapArgs) args[0]).propertyColumn;
            for (var set : map.entrySet()) {
                String column = set.getKey(), property = set.getValue();
                Field field = reflectInfo.clazz.getField(column);
                FieldMapUtils.mapField(obj,mapInfo, resultSet, field, property);
            }
        } catch (NoSuchFieldException e) {
            throw new InvalidException(e);
        }
        return obj;
    }
}
