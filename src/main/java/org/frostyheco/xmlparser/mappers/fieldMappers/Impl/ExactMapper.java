package org.frostyheco.xmlparser.mappers.fieldMappers.Impl;

import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.mappers.fieldMappers.FieldMapper;
import org.frostyheco.xmlparser.mappers.fieldMappers.NameDepends;
import org.frostyheco.xmlparser.mappers.mappingUtils.FieldMapUtils;
import org.frostyheco.xmlparser.mappers.mappingUtils.MapInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.MapArgs;

import java.sql.ResultSet;

public class ExactMapper implements FieldMapper, NameDepends {
    public static final ExactMapper impl = new ExactMapper();

    private ExactMapper() {
    }

    @Override
    public String getColumnName(String fieldName) {
        return fieldName;
    }

    @Override
    public String getFieldName(String columnName) {
        return columnName;
    }

    @Override
    public Object mappingField(Object o,
                               ReflectInfo reflectInfo,
                               MapInfo mapInfo,
                               ResultSet resultSet,
                               MapArgs[] args) throws InvalidException {
        return FieldMapUtils.mapByName(o,reflectInfo,mapInfo,resultSet, this);
    }
}
