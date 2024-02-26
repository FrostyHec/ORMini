package org.frostyheco.xmlparser.mappers.fieldMappers;

import org.frostyheco.xmlparser.mappers.fieldMappers.nonObject.NonObjectType;
import org.frostyheco.xmlparser.mappers.mappingUtils.MapInfo;

public class MapInfoFilter {
    /**
     * Will check and correct fields in map info
     *
     * @param info
     */
    public static void check(MapInfo info) {
        nonObjectCheck(info);
    }

    public static void nonObjectCheck(MapInfo info) {
        var type = NonObjectType.getType(info.pojo);
        if (type != null) {
            info.fieldMapMode = FieldMapMode.SingleColumn;
            info.pojo = type.pojo.getName();
            info.nonObject=true;
        }
    }
}
