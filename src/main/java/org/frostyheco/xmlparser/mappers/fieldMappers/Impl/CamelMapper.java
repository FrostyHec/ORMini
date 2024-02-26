package org.frostyheco.xmlparser.mappers.fieldMappers.Impl;

import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.mappers.fieldMappers.FieldMapper;
import org.frostyheco.xmlparser.mappers.fieldMappers.NameDepends;
import org.frostyheco.xmlparser.mappers.mappingUtils.FieldMapUtils;
import org.frostyheco.xmlparser.mappers.mappingUtils.MapInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.MapArgs;

import java.sql.ResultSet;

public class CamelMapper implements FieldMapper, NameDepends {
    public static final CamelMapper impl = new CamelMapper();

    private CamelMapper() {
    }

    @Override
    public Object mappingField(Object obj, ReflectInfo reflectInfo, MapInfo mapInfo, ResultSet resultSet, MapArgs[] args) throws InvalidException {
        return FieldMapUtils.mapByName(obj, reflectInfo, mapInfo, resultSet, this);
    }

    @Override
    public String getColumnName(String fieldName) {
        StringBuilder snakeCase = new StringBuilder();
        for (int i = 0; i < fieldName.length(); i++) {
            char c = fieldName.charAt(i);
            if (Character.isUpperCase(c)) {
                snakeCase.append("_");
                snakeCase.append(Character.toLowerCase(c));
            } else {
                snakeCase.append(c);
            }
        }
        return snakeCase.toString();
    }

    @Override
    public String getFieldName(String columnName) {
        //hello_world -> helloWorld
        StringBuilder camelCase = new StringBuilder();
        boolean nextUpperCase = false;
        for (int i = 0; i < columnName.length(); i++) {
            char currentChar = columnName.charAt(i);
            if (currentChar == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    camelCase.append(Character.toUpperCase(currentChar));
                    nextUpperCase = false;
                } else {
                    camelCase.append(currentChar);
                }
            }
        }
        return camelCase.toString();
    }
}
