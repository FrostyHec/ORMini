package org.frostyheco.databse.impl;

import org.frostyheco.databse.methods.BasicOperation;
import org.frostyheco.exception.BuildingException;

import javax.sql.DataSource;

//advanced session has a better implementation on methods such as batch...
public class AdvancedSession extends BasicOperation {
    public AdvancedSession(DataSource s,String name) throws BuildingException {
        super(s,name);
    }
}
