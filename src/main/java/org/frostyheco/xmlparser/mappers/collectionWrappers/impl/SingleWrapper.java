package org.frostyheco.xmlparser.mappers.collectionWrappers.impl;

import org.frostyheco.exception.OperationException;
import org.frostyheco.xmlparser.mappers.collectionWrappers.CollectionWrapper;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectInfo;

import java.util.ArrayList;

public class SingleWrapper implements CollectionWrapper {
    public static final SingleWrapper impl=new SingleWrapper();
    private SingleWrapper(){}
    @Override
    public Object wrapObject(ArrayList<Object> objects, ReflectInfo info) throws OperationException {
        if(objects.isEmpty()) return null;
        if(objects.size()!=1) throw new OperationException("Results is not single!");
        return objects.get(0);
    }
}
