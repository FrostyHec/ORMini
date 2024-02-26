package org.frostyheco.xmlparser.mappers.mappingUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

public class ReflectInfo {
    public String packagePath;//pojo
    public Class<?> clazz;
    public Constructor<?> constructor;
    public Field[] fields;
    public Map<String, Field> nameField;
}
