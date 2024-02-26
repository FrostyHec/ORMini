package org.frostyheco.xmlparser.mappers.collectionWrappers.impl;

import org.frostyheco.xmlparser.mappers.collectionWrappers.CollectionWrapper;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ArrayWrapper implements CollectionWrapper {
    public static final ArrayWrapper impl = new ArrayWrapper();

    private ArrayWrapper() {
    }

    @Override
    public Object wrapObject(ArrayList<Object> objects, ReflectInfo info) {
        int size = objects.size();
        Object array = Array.newInstance(info.clazz, size);
        for (int i = 0; i < size; i++) {
            Array.set(array, i, objects.get(i));
        }
        return array;
    }
}
