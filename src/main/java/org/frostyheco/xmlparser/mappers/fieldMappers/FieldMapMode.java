package org.frostyheco.xmlparser.mappers.fieldMappers;

import org.frostyheco.exception.InternalException;
import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.mappers.fieldMappers.Impl.*;

public enum FieldMapMode {
    Exact(ExactMapper.impl, "exact"),
    Camel(CamelMapper.impl, "camel"),
    DirectName(DirectNameMapper.impl, "directName", "direct name"),
    SingleField(SingleFieldMapper.impl, "singleField", "single field"),
    SingleColumn(SingleColumnMapper.impl, "singleColumn", "single column");
    private final String[] names;
    private final FieldMapper impl;

    FieldMapMode(FieldMapper impl, String... names) {
        this.impl = impl;
        this.names = names;
    }

    public FieldMapper getImplClass() {
        if (impl == null) throw new InternalException("FieldMapMode Implementation is null!");
        return impl;
    }

    public String[] getNames() {
        return names;
    }

    public static FieldMapMode getType(String name) throws InvalidException {
        for (var v : FieldMapMode.values()) {
            for (var alias : v.names) {
                if (alias.equalsIgnoreCase(name)) return v;
            }
        }
        throw new InvalidException("Session type not found! name:" + name);
    }
}
