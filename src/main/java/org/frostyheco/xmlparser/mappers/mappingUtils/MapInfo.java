package org.frostyheco.xmlparser.mappers.mappingUtils;

import org.frostyheco.xmlparser.mappers.fieldMappers.MapInfoFilter;
import org.frostyheco.xmlparser.mappers.typeHandler.typeEnum.EnumConvert;
import org.frostyheco.xmlparser.mappers.collectionWrappers.CollectionType;
import org.frostyheco.xmlparser.mappers.fieldMappers.FieldMapMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Basic infos about mappings
 */
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class MapInfo implements Serializable {
    public FieldMapMode fieldMapMode;
    public CollectionType collectionType;
    public String pojo;
    public boolean nonObject;
    public EnumConvert enumConvert;
    public Boolean enumNullable;
    private MapInfo() {
    }
    private MapInfo(FieldMapMode fieldMapMode, CollectionType collectionType, String pojo, boolean nonObject, EnumConvert enumConvert, Boolean enumNullable) {
        this.fieldMapMode = fieldMapMode;
        this.collectionType = collectionType;
        this.pojo = pojo;
        this.nonObject = nonObject;
        this.enumConvert = enumConvert;
        this.enumNullable = enumNullable;
    }
    public static MapInfo getInstance(FieldMapMode fieldMapMode, CollectionType collectionType, String pojo, EnumConvert enumConvert, Boolean enumNullable) {
        MapInfo obj= new MapInfo(fieldMapMode, collectionType, pojo, false, enumConvert, enumNullable);
        MapInfoFilter.check(obj);
        return obj;
    }
    public static MapInfo getInstance(){
        return new MapInfo();
    }
}
