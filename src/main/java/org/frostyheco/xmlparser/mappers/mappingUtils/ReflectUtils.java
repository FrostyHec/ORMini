package org.frostyheco.xmlparser.mappers.mappingUtils;

import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.mappers.fieldMappers.nonObject.DummyNonObject;
import org.frostyheco.xmlparser.mappers.fieldMappers.nonObject.NonObjectType;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ReflectUtils {
    public static ReflectInfo getResult(String name, boolean nonObject, boolean allAccessible) throws InvalidException {
        ReflectInfo res = new ReflectInfo();
        try {
            res.packagePath = name;
            if (nonObject) {
                var type = NonObjectType.getType(name);
                res.clazz = type.pojo;
                res.constructor = DummyNonObject.class.getDeclaredConstructor();
                res.fields = new Field[0];
                res.nameField = new HashMap<>();
            } else {
                res.clazz = Class.forName(name);
                res.constructor = res.clazz.getDeclaredConstructor();
                res.fields = res.clazz.getDeclaredFields();
                res.nameField = getFieldMap(res.fields);
            }
            if (allAccessible) {
                res.constructor.setAccessible(true);
                for (Field value : res.fields) {
                    value.setAccessible(true);
                }
            }
            return res;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new InvalidException(e);
        }
    }

    private static HashMap<String, Field> getFieldMap(Field[] fields) {
        HashMap<String,Field> map = new HashMap<>(fields.length * 2);
        for (var field : fields) {
            map.put(field.getName(), field);
        }
        return map;
    }
}
