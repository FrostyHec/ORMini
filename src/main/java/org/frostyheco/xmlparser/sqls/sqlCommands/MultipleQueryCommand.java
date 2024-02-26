package org.frostyheco.xmlparser.sqls.sqlCommands;

import org.frostyheco.exception.*;
import org.frostyheco.xmlparser.mappers.mappingUtils.MapInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectInfo;
import org.frostyheco.xmlparser.mappers.mappingUtils.ReflectUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleQueryCommand implements QueryCommand {
    private final List<SingleQueryCommand> commands;
    private final String[] sqls;
    private static final boolean forceMap = true;
    private final ReflectInfo reflectInfo;
    private final MapInfo mapInfo;

    public MultipleQueryCommand(List<SingleQueryCommand> commands, MapInfo mapInfo) throws BuildingException {
        try {
            this.commands = commands;
            sqls = commands.stream()
                    .map(SingleQueryCommand::getSQL)
                    .toArray(String[]::new);
            this.mapInfo = mapInfo;
            this.reflectInfo = ReflectUtils.getResult(mapInfo.pojo, mapInfo.nonObject, forceMap);
        } catch (InvalidException e) {
            throw new BuildingException("Creating mapper info false, check pojo definition");
        }
    }

    @Override
    public Object analyze(ResultSet[] resultSets) throws OperationException, SQLException, BuildingException {
        int len = resultSets.length;
        if (len != commands.size())
            throw new OperationException("Unable to map since results sets is not equals to commands length:" +
                    "resultSets length:" + len + "commands length:" + commands.size());
        try {
            Object res = reflectInfo.constructor.newInstance();
            boolean isNullObj = true;
            for (int i = 0; i < len; i++) {
                var cmd = commands.get(i);
                var rst = resultSets[i];
                if (rst.next()) {
                    //TODO verify correctness
                    cmd.mapCurrentTo(res, rst);
                    isNullObj = false;
                }
            }
            return isNullObj ? null : res;
        } catch (InvocationTargetException | InstantiationException e) {
            throw new InternalException(e);
        } catch (IllegalAccessException e) {
            throw new BuildingException("Unable to access the methods, check settings or methods declaration in pojo.", e);
        } catch (InvalidException e) {
            throw new BuildingException("SQL exception occurred while mapping:", e);
        }
    }

    @Override
    public String getSQL() {
        return Arrays.stream(sqls)
                .map(sql -> sql + (sql.endsWith(";") ? "" : ";"))
                .collect(Collectors.joining());
    }

    public String[] getSQLs() {
        return sqls;
    }

    @Override
    public void setSQL(String sql) {
        throw new InternalException(new UnsupportedException("Unsupported to set SQL for multipleQueryCommand"));

    }

}
