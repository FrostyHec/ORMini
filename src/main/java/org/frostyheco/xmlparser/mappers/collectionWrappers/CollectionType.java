package org.frostyheco.xmlparser.mappers.collectionWrappers;

import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.mappers.collectionWrappers.impl.ArrayListWrapper;
import org.frostyheco.xmlparser.mappers.collectionWrappers.impl.ArrayWrapper;
import org.frostyheco.xmlparser.mappers.collectionWrappers.impl.HashsetWrapper;
import org.frostyheco.xmlparser.mappers.collectionWrappers.impl.SingleWrapper;


public enum CollectionType {
    ArrayList("list", ArrayListWrapper.impl ),
    Array("array", ArrayWrapper.impl),
    HashSet("set", HashsetWrapper.impl),
    Single("single", SingleWrapper.impl)
    ;
    private final String name;
    private final CollectionWrapper impl;

    CollectionType(String name, CollectionWrapper impl) {
        this.name = name;
        this.impl = impl;
    }

    public CollectionWrapper getImpl() {
        return impl;
    }

    public String getName() {
        return name;
    }

    public static CollectionType getType(String name) throws InvalidException {
        for (var v : CollectionType.values()) {
            if (v.name.equals(name)) return v;
        }
        throw new InvalidException("Session type not found! name:" + name);
    }
}
