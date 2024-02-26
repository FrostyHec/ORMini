package org.frostyheco.xmlparser.inlines;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.xmlparser.Result;
import org.w3c.dom.Element;

public interface InlineParser {
    String parse(Element e, Result res) throws BuildingException;
}
