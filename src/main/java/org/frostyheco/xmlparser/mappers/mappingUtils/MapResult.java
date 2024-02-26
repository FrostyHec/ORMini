package org.frostyheco.xmlparser.mappers.mappingUtils;

import org.frostyheco.xmlparser.mappers.collectionWrappers.CollectionType;

import java.util.ArrayList;

public class MapResult {
    public final ArrayList<Object> result;
    public final CollectionType type;
    public final ReflectInfo info;

    public MapResult(ArrayList<Object> result, CollectionType type, ReflectInfo info) {
        this.result = result;
        this.type = type;
        this.info = info;
    }
}
