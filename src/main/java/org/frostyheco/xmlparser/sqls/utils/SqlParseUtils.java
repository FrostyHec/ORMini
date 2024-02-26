package org.frostyheco.xmlparser.sqls.utils;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.InternalException;
import org.frostyheco.exception.InvalidException;
import org.frostyheco.xmlparser.Result;
import org.frostyheco.xmlparser.inlines.InlineType;
import org.frostyheco.xmlparser.sqls.StatementType;
import org.frostyheco.xmlparser.sqls.sqlCommands.SimpleCommand;
import org.frostyheco.xmlparser.utils.XMLUtils;
import org.w3c.dom.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SqlParseUtils {
    public static void parseToSimpleCommand(Element e, StatementType type, Result res) throws BuildingException {
        var sqlMap=res.getByType(type);
        NodeList sqlList = e.getElementsByTagName(type.name);
        for (int i = 0; i < sqlList.getLength(); i++) {
            Element sqlElement = (Element) sqlList.item(i);
            String id = sqlElement.getAttribute("id");
            if(XMLUtils.attributeNotAssigned(id)) throw new BuildingException("id cannot be empty! statement type:" + type.name);
            sqlMap.put(id, new SimpleCommand(parseContentToString(sqlElement,res)));
        }
    }
    public static String parseContentToString(Element e, Result res) throws BuildingException {
        NodeList list=e.getChildNodes();
        StringBuilder sql=new StringBuilder();
        for (int i = 0; i < list.getLength(); i++) {
            Node node=list.item(i);
            if(node instanceof Text t){
                sql.append(t.getNodeValue());
            }else if(node instanceof EntityReference t){
                sql.append(t.getNodeValue());
            }else if(node instanceof Element t){
                try {
                    InlineType type = InlineType.getType(t.getNodeName());
                    sql.append(replaceInlines(type,t,res));
                } catch (InvalidException ex) {
                    throw new BuildingException(ex);
                }
            }else if (node instanceof Comment _ignore){
            }else {
                throw new BuildingException("Unexpected node in xml,node name:"+e.getNodeName());
            }
        }
        return sql.toString().trim();
    }
    private static String replaceInlines(InlineType type, Element e, Result res) throws BuildingException {
        Class<?> clazz = type.parser;
        try {
            Method m = clazz.getMethod("parse", Element.class, Result.class);
            Object o = clazz.getConstructor().newInstance();
            return (String) m.invoke(o, e, res);
        }catch (InvocationTargetException ex){
            throw new BuildingException("Exception occurs in parser:",ex);
        }
        catch (NoSuchMethodException | InstantiationException |
                 IllegalAccessException ex) {
            throw new InternalException("Internal Exception occurs when parsing SQL! ", ex);
        }
    }
}
