package org.frostyheco.xmlparser.mappers.query;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.xmlparser.Result;
import org.frostyheco.xmlparser.sqls.sqlCommands.QueryCommand;
import org.w3c.dom.Element;

public interface QueryParsable {
    QueryCommand parse(Element e, Result res) throws BuildingException;
}
