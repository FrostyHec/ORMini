package org.frostyheco.xmlparser;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.InternalException;
import org.frostyheco.xmlparser.sqls.StatementType;
import org.frostyheco.xmlparser.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class XmlParser {
    public static Result parse(InputStream in) throws BuildingException {
        Result res = new Result();
        Document doc;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(in);
            doc.normalize();
        } catch (ParserConfigurationException e) {
            throw new BuildingException("Exceptions occurred in document builder", e);
        } catch (IOException e) {
            throw new BuildingException("IO exception occurred while parsing", e);
        } catch (SAXException e) {
            throw new BuildingException("Exceptions occurred while parsing", e);
        }
        //parse sqls to sets
        for (StatementType type : StatementType.values()) {
            Element rt=getSqlRoot(type.setName, doc);
            if(XMLUtils.elementNotFound(rt)) continue;//no need to analyze an unused set.
            parseSQL(type, rt, res);
        }
        return res;
    }

    public static Element getSqlRoot(String name, Document doc) throws BuildingException {
        NodeList list = doc.getElementsByTagName(name);
        int len = list.getLength();
        return switch (len) {
            case 0 -> null;
            case 1 -> (Element) list.item(0);
            default -> throw new BuildingException("NodeList length unexpected! " +
                    "Check if duplicated in xml, set name:" + name + "length:" + len);
        };
    }

    private static void parseSQL(StatementType type, Element e, Result res) throws BuildingException {
        Class<?> clazz = type.parser;
        try {
            Method m = clazz.getMethod("parse", Element.class, Result.class);
            Object o = clazz.getConstructor().newInstance();
            m.invoke(o, e, res);
        } catch (InvocationTargetException ex){
            throw new BuildingException("Parsing xml failed! type:"+type.name,ex);
        }catch (NoSuchMethodException | InstantiationException |
                 IllegalAccessException ex) {
            throw new InternalException("Internal Exception occurs when parsing SQL! ", ex);
        }
    }
}
