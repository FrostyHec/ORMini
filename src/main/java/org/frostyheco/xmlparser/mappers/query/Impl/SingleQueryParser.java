package org.frostyheco.xmlparser.mappers.query.Impl;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.settings.Settings;
import org.frostyheco.xmlparser.Result;
import org.frostyheco.xmlparser.mappers.mappingUtils.MapInfo;
import org.frostyheco.xmlparser.mappers.query.QueryParsable;
import org.frostyheco.xmlparser.sqls.sqlCommands.QueryCommand;
import org.frostyheco.xmlparser.sqls.sqlCommands.SingleQueryCommand;
import org.frostyheco.xmlparser.sqls.utils.SqlParseUtils;
import org.frostyheco.xmlparser.utils.XMLUtils;
import org.w3c.dom.Element;

public class SingleQueryParser implements QueryParsable {
    @Override
    public QueryCommand parse(Element e, Result res) throws BuildingException {
        String sql = SqlParseUtils.parseContentToString(e, res);
        MapInfo mapInfo = MapInfo.getInstance(
                XMLUtils.getAttributeType(e, "mapMode", Settings.impl.defaultFieldMapMode()),
                XMLUtils.getAttributeType(e, "collection", Settings.impl.defaultCollection()),
                XMLUtils.getStringAttribute(e,"pojo",true),

                XMLUtils.getAttributeType(e, "enumConvert", Settings.impl.defaultEnumConvert()),
                XMLUtils.getBooleanAttribute(e, "enumNullable", Settings.impl.defaultEnumNullable())
        );
        return new SingleQueryCommand(sql, mapInfo);
    }
}
