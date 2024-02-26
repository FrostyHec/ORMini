package org.frostyheco.settings;


import org.frostyheco.databse.SessionType;
import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.InvalidException;
import org.frostyheco.utils.EnumUtils;
import org.frostyheco.xmlparser.mappers.typeHandler.typeEnum.EnumConvert;
import org.frostyheco.xmlparser.mappers.collectionWrappers.CollectionType;
import org.frostyheco.xmlparser.mappers.fieldMappers.FieldMapMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings {
    public static Settings impl=new Settings();
    private static final String settingPath = "/ORMini.yml";

    private final SettingInfo settingInfo;
    private final Map<String, FileDetails> xmlPathMap = new HashMap<>();

    private Settings(){
        Yaml yaml = new Yaml();
        SettingPOJO o = yaml.loadAs(this.getClass().getResourceAsStream(settingPath), SettingPOJO.class);
        settingInfo = o.settings;
        o.xmlPath.file.forEach(file -> {
            String k = file.name;
            if (xmlPathMap.containsKey(k))
                throw new RuntimeException(new BuildingException("xmlPath settings name duplicated! name:" + k));
            xmlPathMap.put(k, new FileDetails(file.sqlPath));
        });
    }

    public SessionType sessionType() throws BuildingException {
        String s=settingInfo.sessionType;
        if(s==null) return SessionType.Basic;//Default session is basic.
        try {
            return SessionType.getType(s);
        } catch (InvalidException e) {
            throw new BuildingException(e);
        }
    }

    public String sqlPathOf(String name) throws BuildingException {
        if (!xmlPathMap.containsKey(name)) throw new BuildingException("xmlPath key name not found! name:" + name);
        return xmlPathMap.get(name).sqlPath;
    }
    public FieldMapMode defaultFieldMapMode() throws BuildingException {
        return EnumUtils.stringToEnum(settingInfo.mapper.fieldMapMode,FieldMapMode.Camel);
    }
    public CollectionType defaultCollection() throws BuildingException {
        return EnumUtils.stringToEnum(settingInfo.mapper.defaultCollection,CollectionType.ArrayList);
    }
    public EnumConvert defaultEnumConvert() throws BuildingException {
        return EnumUtils.stringToEnum(
                settingInfo.mapper.typeEnum.defaultEnumConvert,
                EnumConvert.Ordinal);
    }
    public boolean defaultEnumNullable() {
        Boolean b=settingInfo.mapper.typeEnum.defaultEnumNullable;
        return b==null?true:b;//default true
    }
}

@Data
class SettingPOJO {
    public SettingInfo settings;
    public XmlPath xmlPath;
}

@Data
class SettingInfo {
    public String sessionType;
    public Mapper mapper;
}

@Data
class Mapper {
    public String fieldMapMode;
    public String defaultCollection;
    public TypeEnum typeEnum;
}
@Data
class TypeEnum{
    public String defaultEnumConvert;
    public Boolean defaultEnumNullable;
}
@Data
class XmlPath {
    public List<File> file;
}

@Data
class File {
    public String name;
    public String sqlPath;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class FileDetails {
    public String sqlPath;
}