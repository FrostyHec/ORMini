package org.frostyheco.xmlparser.mappers.fieldMappers.Impl;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.InvalidException;
import org.frostyheco.exception.OperationException;
import org.frostyheco.xmlparser.mappers.fieldMappers.FieldMapper;
import org.frostyheco.xmlparser.mappers.mappingUtils.FieldMapUtils;
import org.frostyheco.xmlparser.mappers.mappingUtils.MapInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.Impl.SingleFieldMapArgs;
import org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.MapArgs;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleFieldMapper implements FieldMapper {
    public static final SingleFieldMapper impl = new SingleFieldMapper();

    private SingleFieldMapper() {
    }

    @Override
    public Object mappingField(Object target,
                               ReflectInfo reflectInfo,
                               MapInfo mapInfo,
                               ResultSet resultSet,
                               MapArgs[] args) throws InvalidException {
        SingleFieldMapArgs arg = (SingleFieldMapArgs) args[0];
        try {//TODO BUGS HERE!
            resultSet.previous();//since resultSet here got has been pointed to 1
            Object val = arg.originCommand.analyze(new ResultSet[]{resultSet});

            Field field = reflectInfo.nameField.get(arg.field);
            if (field == null) throw new NoSuchFieldException("No such field:" + arg.field);

            return FieldMapUtils.mapField(target, val, mapInfo, field);
        } catch (SQLException | BuildingException | OperationException | NoSuchFieldException e) {
            throw new InvalidException(e);
        }
    }
}
