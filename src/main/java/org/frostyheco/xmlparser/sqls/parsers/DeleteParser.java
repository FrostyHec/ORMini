package org.frostyheco.xmlparser.sqls.parsers;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.xmlparser.Result;
import org.frostyheco.xmlparser.sqls.SQLParser;
import org.frostyheco.xmlparser.sqls.StatementType;
import org.frostyheco.xmlparser.sqls.utils.SqlParseUtils;
import org.w3c.dom.Element;

public class DeleteParser implements SQLParser {
    @Override
    public void parse(Element e, Result res) throws BuildingException {
        SqlParseUtils.parseToSimpleCommand(e, StatementType.Delete, res);
    }
}
