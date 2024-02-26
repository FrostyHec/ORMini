package org.frostyheco.databse.impl;

import org.frostyheco.databse.methods.BasicOperation;
import org.frostyheco.exception.BuildingException;

import javax.sql.DataSource;

public class BasicSession extends BasicOperation {
    public BasicSession(DataSource s, String name) throws BuildingException {
        super(s,name);
    }
}
