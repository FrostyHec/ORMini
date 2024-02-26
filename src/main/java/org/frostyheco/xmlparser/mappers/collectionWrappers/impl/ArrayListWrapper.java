package org.frostyheco.xmlparser.mappers.collectionWrappers.impl;

import org.frostyheco.xmlparser.mappers.collectionWrappers.CollectionWrapper;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectInfo;

import java.util.ArrayList;

public class ArrayListWrapper implements CollectionWrapper {
    public static final ArrayListWrapper impl=new ArrayListWrapper();
    private ArrayListWrapper(){}
    @Override
    public Object wrapObject(ArrayList<Object> objects, ReflectInfo info) {
        return (ArrayList<?>) objects;
    }
}
