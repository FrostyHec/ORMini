package org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.Impl;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.xmlparser.mappers.collectionWrappers.CollectionType;
import org.frostyheco.xmlparser.mappers.fieldMappers.FieldMapMode;
import org.frostyheco.xmlparser.mappers.mappingUtils.MapInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.MapArgs;
import org.frostyheco.xmlparser.mappers.typeHandler.typeEnum.EnumConvert;
import org.frostyheco.xmlparser.sqls.sqlCommands.SingleQueryCommand;

public class SingleFieldMapArgs implements MapArgs {
    public final String field;
    public final MapInfo mapInfo;
    public final SingleQueryCommand originCommand;

    public SingleFieldMapArgs(String pojo, String sql, FieldMapMode mapMode, String field, CollectionType collectionType, EnumConvert enumConvert, Boolean enumNullable) throws BuildingException {
        this.field = field;
        mapInfo = MapInfo.getInstance(mapMode, collectionType, pojo, enumConvert, enumNullable);
        originCommand = new SingleQueryCommand(sql, mapInfo);
    }
}
