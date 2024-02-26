package org.frostyheco.xmlparser.inlines.Impl;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.Result;
import org.frostyheco.xmlparser.inlines.InlineParser;
import org.frostyheco.xmlparser.sqls.StatementType;
import org.frostyheco.xmlparser.sqls.utils.SqlParseUtils;
import org.frostyheco.xmlparser.utils.XMLUtils;
import org.w3c.dom.Element;

public class IncludeParser implements InlineParser {
    @Override
    public String parse(Element e, Result res) throws BuildingException {
        StatementType type;
        try {
            type = StatementType.getTypeByName(e.getAttribute("type"));
        } catch (InvalidException ex) {
            throw new BuildingException(ex);
        }
        String id = e.getAttribute("id");
        var map = res.getByType(type);
        if (map.size() != 0) {//have been initialized
            return map.get(id).getSQL();
        }
        //haven't been initialized
        return SqlParseUtils.parseContentToString(XMLUtils.getElementByID(e, type, id), res);
    }
}
