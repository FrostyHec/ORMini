package org.frostyheco.databse;

import org.frostyheco.databse.impl.AdvancedSession;
import org.frostyheco.databse.impl.BasicSession;
import org.frostyheco.exception.InvalidException;

public enum SessionType {
    //name should not be duplicated!
    Basic("basic", BasicSession.class),
    Advanced("advanced", AdvancedSession.class);
    private final String name;
    private final Class<?> implClass;

    SessionType(String name, Class<?> implClass) {
        this.name = name;
        this.implClass = implClass;
    }

    public Class<?> getImplClass() {
        return implClass;
    }

    public String getName() {
        return name;
    }

    public static SessionType getType(String name) throws InvalidException {
        for (var v : SessionType.values()) {
            if (v.name.equals(name)) return v;
        }
        throw new InvalidException("Session type not found! name:" + name);
    }
}
