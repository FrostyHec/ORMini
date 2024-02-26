package org.frostyheco.xmlparser.mappers.fieldMappers;

import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.mappers.mappingUtils.MapInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.MapArgs;

import java.sql.ResultSet;

public interface FieldMapper {

    Object mappingField(Object obj, ReflectInfo reflectInfo, MapInfo mapInfo, ResultSet resultSet, MapArgs[] args) throws InvalidException;

    static FieldMapper getMappers(FieldMapMode mode) {
        return mode.getImplClass();
    }
}
