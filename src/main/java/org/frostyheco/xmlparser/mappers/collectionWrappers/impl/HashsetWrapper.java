package org.frostyheco.xmlparser.mappers.collectionWrappers.impl;

import org.frostyheco.exception.OperationException;
import org.frostyheco.xmlparser.mappers.collectionWrappers.CollectionWrapper;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectInfo;

import java.util.ArrayList;
import java.util.HashSet;

public class HashsetWrapper implements CollectionWrapper {
    public static final HashsetWrapper impl=new HashsetWrapper();
    private HashsetWrapper(){}

    @Override
    public Object wrapObject(ArrayList<Object> objects, ReflectInfo info) throws OperationException {
        return new HashSet<>(objects);
    }
}
