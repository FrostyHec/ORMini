package org.frostyheco.xmlparser.sqls;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.xmlparser.Result;
import org.w3c.dom.Element;

public interface SQLParser{
    void parse(Element e, Result res) throws BuildingException;
}
