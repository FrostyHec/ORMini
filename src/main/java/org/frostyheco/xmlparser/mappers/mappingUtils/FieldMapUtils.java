package org.frostyheco.xmlparser.mappers.mappingUtils;

import org.frostyheco.exception.InternalException;
import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.mappers.fieldMappers.NameDepends;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FieldMapUtils {
    public static Object mapField(Object o, Object val, MapInfo mapInfo, Field field) throws InvalidException {
        try {
            Class<?> fieldType = field.getType();
            //Code below is pretty awful.
            if (fieldType.isEnum()) {//Enum type handler
                val = mapInfo.enumConvert.getImpl().getObject(fieldType, mapInfo.enumNullable, val);
                field.set(o, fieldType.cast(val));
            } else if (fieldType.isPrimitive()) {//Primitive type handler
                if (fieldType.equals(byte.class)) {
                    val = Byte.valueOf(val.toString());
                } else if (fieldType.equals(short.class)) {
                    val = Short.valueOf(val.toString());
                } else if (fieldType.equals(int.class)) {
                    val = Integer.valueOf(val.toString());
                } else if (fieldType.equals(long.class)) {
                    val = Long.valueOf(val.toString());
                } else if (fieldType.equals(float.class)) {
                    val = Float.valueOf(val.toString());
                } else if (fieldType.equals(double.class)) {
                    val = Double.valueOf(val.toString());
                } else if (fieldType.equals(char.class)) {
                    val = val.toString().charAt(0);
                } else if (fieldType.equals(boolean.class)) {
                    val = Boolean.valueOf(val.toString());
                } else {
                    throw new InternalException("Unknown primitive type:" + fieldType);
                }
                field.set(o, val);
            } else {//Object, cast directly
                field.set(o, fieldType.cast(val));
            }
            return o;
        } catch (IllegalAccessException e) {
            throw new InvalidException(e);
        }
    }

    public static Object mapField(Object o,
                                  MapInfo mapInfo,
                                  ResultSet resultSet,
                                  Field field,
                                  String columnName) throws InvalidException {
        try {
            Object val = resultSet.getObject(columnName);
            return mapField(o, val, mapInfo, field);
        } catch (SQLException e) {
            throw new InvalidException(e);
        }
    }

    public static Object mapByName(Object o,
                                   ReflectInfo reflectInfo,
                                   MapInfo mapInfo,
                                   ResultSet resultSet,
                                   NameDepends nameDepends) throws InvalidException {
        try {
            var rsData = resultSet.getMetaData();
            int rsCnt = rsData.getColumnCount();
            if (rsCnt >= reflectInfo.fields.length) {
                //结果列比object字段多
                for (Field field : reflectInfo.fields) {
                    mapField(o, mapInfo, resultSet, field, nameDepends.getColumnName(field.getName()));
                }
            } else {
                //结果列比obj字段少
                for (int i = 1; i <= rsCnt; i++) {
                    String columnName = rsData.getColumnLabel(i);
                    Field field = reflectInfo.nameField.get(nameDepends.getFieldName(columnName));
                    mapField(o,mapInfo,resultSet,field,columnName);
                }
            }
        } catch (SQLException e) {
            throw new InvalidException(e);
        }
        return o;
    }
}
