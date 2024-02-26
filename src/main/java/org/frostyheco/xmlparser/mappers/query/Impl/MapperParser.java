package org.frostyheco.xmlparser.mappers.query.Impl;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.InternalException;
import org.frostyheco.xmlparser.Result;
import org.frostyheco.xmlparser.mappers.fieldMappers.FieldMapMode;
import org.frostyheco.xmlparser.mappers.mappingUtils.MapInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.Impl.NameMapArgs;
import org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.Impl.SingleFieldMapArgs;
import org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.MapArgs;
import org.frostyheco.xmlparser.mappers.query.QueryParsable;
import org.frostyheco.xmlparser.mappers.query.QueryType;
import org.frostyheco.xmlparser.sqls.StatementType;
import org.frostyheco.xmlparser.sqls.sqlCommands.MultipleQueryCommand;
import org.frostyheco.xmlparser.sqls.sqlCommands.QueryCommand;
import org.frostyheco.xmlparser.sqls.sqlCommands.SingleQueryCommand;
import org.frostyheco.xmlparser.utils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapperParser implements QueryParsable {
    @Override
    public QueryCommand parse(Element e, Result res) throws BuildingException {
        MapInfo mapInfo = MapInfo.getInstance();
        mapInfo.pojo = XMLUtils.getStringAttribute(e, "pojo", true);
        NodeList useElementList = e.getElementsByTagName("use");
        List<SingleQueryCommand> commands = new ArrayList<>();
        for (int i = 0; i < useElementList.getLength(); i++) {
            Element useElement = (Element) useElementList.item(i);
            SingleQueryCommand cmd;
            //It's ugly, maybe find some ways to improve it later.
            if (useElement.getElementsByTagName("result").getLength() != 0)
                //使用result自定义map模式
                cmd = defineMapMode(useElement, mapInfo, res);
            else if (!XMLUtils.attributeNotAssigned(useElement.getAttribute("field")))
                //field存在，则将结果赋入一个字段中
                cmd = fieldMapMode(useElement, mapInfo, res);
            else cmd = givenMapMode(useElement, mapInfo, res);//基础模式，直接复用原先的query
            commands.add(cmd);
        }
        return new MultipleQueryCommand(commands, mapInfo);
    }

    private SingleQueryCommand fieldMapMode(Element e, MapInfo rootInfo, Result res) throws BuildingException {
        SingleQueryCommand command = getSingleQueryCommand(e, res);
        try {
            String sql = command.getSQL();
            MapInfo info = command.cloneMapInfo();
            var arg = new SingleFieldMapArgs(
                    XMLUtils.getStringAttribute(e, "pojo", info.pojo),
                    sql,
                    XMLUtils.getAttributeType(e, "mapMode", info.fieldMapMode),
                    XMLUtils.getStringAttribute(e, "field", true),
                    XMLUtils.getAttributeType(e, "collection", info.collectionType),
                    XMLUtils.getAttributeType(e, "enumConvert", info.enumConvert),
                    XMLUtils.getBooleanAttribute(e, "enumNullable", info.enumNullable)
            );//TODO check correctness
            info.fieldMapMode = FieldMapMode.SingleField;//改为SingleField模式
            info.pojo = rootInfo.pojo;//修改pojo为mapper定义的
            info.nonObject=false;//修改为非nonObject
            return new SingleQueryCommand(sql, info, new MapArgs[]{arg});
        } catch (IOException | ClassNotFoundException ex) {
            throw new InternalException("Internal Exception occurs while cloning mapInfo", ex);
        }
    }

    private SingleQueryCommand givenMapMode(Element e, MapInfo rootInfo, Result res) throws BuildingException {
        SingleQueryCommand command = getSingleQueryCommand(e, res);
        try {
            String sql = command.getSQL();
            MapInfo info = command.cloneMapInfo();
            info.pojo = rootInfo.pojo;//修改pojo为mapper定义的//TODO field?
            info.fieldMapMode = XMLUtils.getAttributeType(e,"mapMode",info.fieldMapMode);
            info.enumConvert=XMLUtils.getAttributeType(e,"enumConvert",info.enumConvert);
            info.enumNullable=XMLUtils.getBooleanAttribute(e,"enumNullable",info.enumNullable);
            return new SingleQueryCommand(sql, info);
        } catch (IOException | ClassNotFoundException ex) {
            throw new InternalException("Internal Exception occurs while cloning mapInfo", ex);
        }
    }

    private SingleQueryCommand getSingleQueryCommand(Element e, Result res) throws BuildingException {
        String queryID = e.getAttribute("query");
        if (XMLUtils.attributeNotAssigned(queryID)) throw new BuildingException("Query reference id not defined!");
        if (res.queries.containsKey(queryID)) {//query已被解析
            var t = res.queries.get(queryID);
            if (t instanceof SingleQueryCommand s) return s;
            else throw new BuildingException("using command not single!");
        } else {//query没被解析
            Element queryE = XMLUtils.getElementByID(e, StatementType.Query, queryID);
            return (SingleQueryCommand) QueryType.Query.parser.parse(queryE, res);
        }
    }

    private SingleQueryCommand defineMapMode(Element e, MapInfo rootInfo, Result res) throws BuildingException {
        //解析内部的映射方式
        NodeList resultList = e.getElementsByTagName("result");
        NameMapArgs nameMapArgs = new NameMapArgs();
        for (int i = 0; i < resultList.getLength(); i++) {
            Element resultE = (Element) resultList.item(i);
            String column = resultE.getAttribute("column");
            String property = resultE.getAttribute("property");
            if (nameMapArgs.propertyColumn.containsKey(property))
                throw new BuildingException("property mapping duplicated! property:" + property);
            nameMapArgs.propertyColumn.put(property, column);
        }
        //获得使用的query信息
        SingleQueryCommand command = getSingleQueryCommand(e, res);
        //修改信息生成一个新的queryCommand
        try {
            String sql = command.getSQL();
            MapInfo info = command.cloneMapInfo();
            info.fieldMapMode = FieldMapMode.DirectName;//改为directName模式
            info.pojo = rootInfo.pojo;//修改pojo为mapper定义的
            info.enumConvert = XMLUtils.getAttributeType(e, "enumConvert", info.enumConvert);
            info.enumNullable = XMLUtils.getBooleanAttribute(e, "enumNullable", info.enumNullable);
            return new SingleQueryCommand(sql, info, new MapArgs[]{nameMapArgs});
        } catch (IOException | ClassNotFoundException ex) {
            throw new InternalException("Internal Exception occurs while cloning mapInfo", ex);
        }
    }
}
