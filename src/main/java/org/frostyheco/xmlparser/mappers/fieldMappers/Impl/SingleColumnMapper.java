package org.frostyheco.xmlparser.mappers.fieldMappers.Impl;

import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.mappers.fieldMappers.FieldMapper;
import org.frostyheco.xmlparser.mappers.mappingUtils.MapInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.MapArgs;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleColumnMapper implements FieldMapper {
    public static final SingleColumnMapper impl = new SingleColumnMapper();

    private SingleColumnMapper() {
    }

    @Override
    public Object mappingField(Object obj,
                               ReflectInfo reflectInfo,
                               MapInfo mapInfo,
                               ResultSet resultSet,
                               MapArgs[] args) throws InvalidException {
        try {
            return resultSet.getObject(1);
        } catch (SQLException e) {
            throw new InvalidException(e);
        }
    }
}
