package org.frostyheco.xmlparser.mappers.collectionWrappers;

import org.frostyheco.exception.OperationException;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectInfo;

import java.util.ArrayList;

public interface CollectionWrapper {
    Object wrapObject(ArrayList<Object> objects, ReflectInfo info) throws OperationException;
    static CollectionWrapper getInstance(CollectionType type) {
        return type.getImpl();
    }
}
