package org.frostyheco.databse;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.InternalException;
import org.frostyheco.settings.Settings;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SessionFactory {
    //TODO quite doubtful to use hashmap however,since there might not have xml to be parsed twice.
    //Not yet, I use it twice now~
    private static final Map<String, Session> factory = new HashMap<>(21);//assume to be at most 15 xml doc

    public static SessionType getImplType() throws BuildingException {
        return Settings.impl.sessionType();
    }

    public static String getSqlPath(String name) throws BuildingException {
        return Settings.impl.sqlPathOf(name);
    }

    public static Session create(DataSource source, String name) throws BuildingException {
        if (factory.containsKey(name)) return factory.get(name);
        try {
            Constructor<?> c = getImplType().getImplClass().getConstructor(DataSource.class, String.class);
            Session s = (Session) c.newInstance(source, name);
            factory.put(name, s);
            return s;
        } catch (NoSuchMethodException e) {
            throw new InternalException("Service implementation constructor not found!", e);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InternalException("constructing failed!", e);
        } catch (InvocationTargetException e) {
            throw new BuildingException(e);
        }
    }
}
