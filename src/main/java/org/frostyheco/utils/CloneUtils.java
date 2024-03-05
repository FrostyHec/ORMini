package org.frostyheco.utils;

import org.frostyheco.exception.InvalidException;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class CloneUtils {
    public static final Map<Class<?>, List<Object>> emptyFieldNullMap = Collections.unmodifiableMap(new HashMap<>());
    public static final Map<Class<?>, List<Object>> sampleFieldNullMap = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(String.class, List.of(""))
    );

    /**
     * DeepClone using serializable.
     *
     * @param obj
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepClone(T obj) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (T) ois.readObject();
    }

    public static <T> T fillDefault(T settingA, T defaultSettingB, Set<Class<?>> userDefinedType) throws InvalidException, IllegalAccessException {
        return fillDefault(settingA, defaultSettingB, emptyFieldNullMap, userDefinedType);
    }

    public static <T> T fillDefault(T settingA, T defaultSettingB, Map<Class<?>, List<Object>> customizedFieldNullMap, Set<Class<?>> userDefinedType) throws InvalidException, IllegalAccessException {
        Field[] fields = settingA.getClass().getDeclaredFields();
        for (Field field : fields) {
            Class<?> type = field.getType();
            field.setAccessible(true);
            Object valueA = field.get(settingA);
            Object valueB = field.get(defaultSettingB);
            if (valueA == null || customizeFieldNullCheck(valueA, customizedFieldNullMap)) {
                field.set(settingA, valueB);
            } else if (userDefinedType.contains(type)) {
                fillDefault(valueA, valueB, customizedFieldNullMap, userDefinedType);
            }
        }
        return settingA;
    }

    private static boolean customizeFieldNullCheck(Object o, Map<Class<?>, List<Object>> costomizedFieldNullMap){
        //找到类型后比较下是不是空值
        for (var e : costomizedFieldNullMap.entrySet()) {
            if (o.getClass().equals(e.getKey())) {
                for (var l : e.getValue()) {
                    if (o.equals(l)) return true;
                }
                return false;
            }
        }
        return false;
    }
}
