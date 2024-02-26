package org.frostyheco.xmlparser.sqls.sqlCommands;

import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.InternalException;
import org.frostyheco.exception.InvalidException;
import org.frostyheco.exception.OperationException;
import org.frostyheco.utils.CloneUtils;
import org.frostyheco.xmlparser.mappers.collectionWrappers.CollectionWrapper;
import org.frostyheco.xmlparser.mappers.fieldMappers.FieldMapper;
import org.frostyheco.xmlparser.mappers.mappingUtils.MapInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectUtils;
import org.frostyheco.xmlparser.mappers.mappingUtils.mapArgs.MapArgs;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SingleQueryCommand implements QueryCommand {
    private String sql;
    //handler
    private static final boolean forceMap = true;
    private final FieldMapper fieldMapper;
    private final CollectionWrapper collectionWrapper;
    //Infos
    private final ReflectInfo reflectInfo;
    private final MapInfo mapInfo;
    private final MapArgs[] args;

    //will automatically set constructor of field accessible even if declared private
    //For enum cases we won't create new obj, however. exception will throw if mapping mode undefined.
    public SingleQueryCommand(String sql, MapInfo mapInfo) throws BuildingException {
        this(sql, mapInfo, null, null);
    }

    public SingleQueryCommand(String sql, MapInfo mapInfo, MapArgs[] args) throws BuildingException {
        this(sql, mapInfo, null, args);
    }

    public SingleQueryCommand(String sql, MapInfo mapInfo, ReflectInfo reflectInfo, MapArgs[] args) throws BuildingException {
        try {
            this.sql = sql;
            fieldMapper = FieldMapper.getMappers(mapInfo.fieldMapMode);
            collectionWrapper = CollectionWrapper.getInstance(mapInfo.collectionType);
            this.mapInfo = mapInfo;
            this.reflectInfo = reflectInfo == null ? ReflectUtils.getResult(mapInfo.pojo,mapInfo.nonObject, forceMap) : reflectInfo;
            this.args = args;
        } catch (InvalidException e) {
            throw new BuildingException("Creating mapper info false, check pojo definition", e);
        }
    }

    /**
     * @param target
     * @param resultSet be careful that resultSet is at least start at row 1(one call on resultSet.next() had been executed)
     * @throws InvalidException if bad things happened or you're longzhi!
     */

    public Object mapCurrentTo(Object target, ResultSet resultSet) throws InvalidException {
        return fieldMapper.mappingField(target, reflectInfo, mapInfo, resultSet, args);
    }

    @Override
    public Object analyze(ResultSet[] resultSets) throws OperationException, BuildingException, SQLException {
        ArrayList<Object> res = new ArrayList<>();
        ResultSet resultSet = resultSets[0];
        try {
            while (resultSet.next()) {
                Object temp=reflectInfo.constructor.newInstance();
                Object o = mapCurrentTo(temp, resultSet);//必须用新的Object，不能假定能够直接在Object上进行修改
                res.add(o);
            }
        } catch (InvocationTargetException | InstantiationException e) {
            throw new InternalException(e);
        } catch (IllegalAccessException e) {
            throw new BuildingException("Unable to access the methods, check settings or methods declaration in pojo.", e);
        } catch (InvalidException e) {
            throw new BuildingException("SQL exception occurred while mapping:", e);
        }
        return collectionWrapper.wrapObject(res, reflectInfo);
    }

    @Override
    public String getSQL() {
        return sql;
    }

    @Override
    public String[] getSQLs() {
        return new String[]{sql};
    }

    @Override
    public void setSQL(String sql) {
        this.sql = sql;
    }

    public MapInfo cloneMapInfo() throws IOException, ClassNotFoundException {
        return CloneUtils.deepClone(mapInfo);
    }

    public boolean isForceMap() {
        return forceMap;
    }
}
