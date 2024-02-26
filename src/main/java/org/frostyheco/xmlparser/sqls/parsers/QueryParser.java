package org.frostyheco.xmlparser.sqls.parsers;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.xmlparser.Result;
import org.frostyheco.xmlparser.mappers.query.QueryType;
import org.frostyheco.xmlparser.sqls.SQLParser;
import org.frostyheco.xmlparser.sqls.sqlCommands.QueryCommand;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class QueryParser implements SQLParser {
    @Override
    public void parse(Element e, Result res) throws BuildingException {
        for (var type: QueryType.values()) {
            NodeList queryList=e.getElementsByTagName(type.name);
            for (int i = 0; i < queryList.getLength(); i++) {
                Element sqlElement = (Element) queryList.item(i);
                String id = sqlElement.getAttribute("id");
                if(id.equals("")) throw new BuildingException("id cannot be empty! query type:"+type.name);
                //get obj
                QueryCommand cmd=type.parser.parse(sqlElement,res);
                res.queries.put(id,cmd);
            }
        }
    }
}
