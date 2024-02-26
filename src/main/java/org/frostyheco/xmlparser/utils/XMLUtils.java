package org.frostyheco.xmlparser.utils;


import org.frostyheco.exception.BuildingException;
import org.frostyheco.utils.EnumUtils;
import org.frostyheco.xmlparser.XmlParser;
import org.frostyheco.xmlparser.sqls.StatementType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLUtils {
    public static boolean attributeNotAssigned(String val) {
        return val.equals("");
    }

    public static boolean elementNotFound(Element e) {
        return e == null;
    }

    //if an attribute can be mapped to an enum with static string getter, we can use this method to get it.
    public static <T extends Enum<T>> T getAttributeType(Element e, String attributeName, T defaultVal) throws BuildingException {
        return EnumUtils.stringToEnum(e.getAttribute(attributeName), defaultVal);
    }

    public static String getStringAttribute(Element e, String attributeName, boolean mustAssigned) throws BuildingException {
        String s = e.getAttribute(attributeName);
        if (mustAssigned && attributeNotAssigned(s))
            throw new BuildingException("Attribute not assigned! name:" + attributeName);
        return s;
    }

    public static String getStringAttribute(Element e, String attributeName, String defaultVal) {
        String s = e.getAttribute(attributeName);
        return attributeNotAssigned(s) ? defaultVal : s;
    }

    public static boolean getBooleanAttribute(Element e, String attributeName, boolean defaultVal) throws BuildingException {
        String b = e.getAttribute(attributeName);
        if (XMLUtils.attributeNotAssigned(b)) return defaultVal;
        else if ("true".equalsIgnoreCase(b)) return true;
        else if ("false".equalsIgnoreCase(b)) return false;
        else throw new BuildingException("Illegal boolean attribute val:" + b);
    }

    public static Element getElementByID(Element cur, StatementType type, String id) throws BuildingException {
        Document doc = cur.getOwnerDocument();
        Element root = XmlParser.getSqlRoot(type.setName, doc);
        if (XMLUtils.elementNotFound(root))
            throw new BuildingException("include type can't be find in xml, check if defined");

        NodeList sqlList = root.getElementsByTagName(type.name);
        for (int i = 0; i < sqlList.getLength(); i++) {
            Element sqlElement = (Element) sqlList.item(i);
            if (!sqlElement.getAttribute("id").equals(id)) continue;
            return sqlElement;//find the matched id
        }
        throw new BuildingException("id not found!");
    }
}
