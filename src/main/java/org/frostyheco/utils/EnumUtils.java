package org.frostyheco.utils;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.InternalException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumUtils {
    private EnumUtils(){}
    public static <T> T stringToEnum(String typeVal, T defaultEnum) throws BuildingException {
        return stringToEnum(typeVal,new String[]{""},defaultEnum,"getType");
    }
    @SuppressWarnings("unchecked")
    public static <T> T stringToEnum(String typeVal, String[] emptyVal, T defaultEnum, String getterName) throws BuildingException {
        if(typeVal==null) return defaultEnum;
        for (String s:emptyVal) {
            if(typeVal.equals(s)) return defaultEnum;
        }
        try {
            Class<?> clazz=defaultEnum.getClass();
            Method m=clazz.getMethod(getterName,String.class);
            return (T) m.invoke(null,typeVal);
        } catch (InvocationTargetException ex) {
            throw new BuildingException("mapMode invalid! val:"+typeVal,ex);
        } catch (NoSuchMethodException | IllegalAccessException ex) {
            throw new InternalException("Internal exception occurs when getting attribute type!",ex);
        }
    }
}
