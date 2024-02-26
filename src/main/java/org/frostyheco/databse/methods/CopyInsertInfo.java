package org.frostyheco.databse.methods;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;

public abstract class CopyInsertInfo<T> {
    public static final String delimiter = ",";
    private static final String defaultTempPath = generateDefaultPath();
    private BufferedWriter writer;
    private final String tableName;
    private final String path;
    private final String pathName;
    private long cnt;

    //for session to use
    private static String generateDefaultPath() {
        String originPath = CopyInsertInfo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String sep = "/";//System.getProperty("file.separator");
        //String originPath = System.getProperty("java.io.tmpdir");
        File jarFile = new File(originPath);
        if (originPath.endsWith(".jar")) {
            originPath = jarFile.getParent();
        }
        if (originPath.startsWith(sep)) {
            originPath = originPath.substring(1);
        }
        return originPath + (originPath.endsWith(sep) ? "" : sep) + "yourTmp";
    }

    public final long printData(List<T> list) throws IOException {
        cnt = 0;
        Path dir = Path.of(path);
        Path file = Path.of(pathName);
        deleteTmpIfExist();
        if(!Files.exists(dir))Files.createDirectory(dir);
        Files.createFile(file);
        try (BufferedWriter w = new BufferedWriter(new FileWriter(pathName))) {
            this.writer = w;
            for (var o : list) {
                insert(o);
            }
            return cnt;
        }
    }

    private void printData(String[] vals) throws IOException {
        StringJoiner joiner = new StringJoiner(delimiter, "", "\n");
        for (var val : vals) {
            joiner.add(val);
        }
        writer.write(joiner.toString());
    }

    public final void deleteTmpIfExist() throws IOException {
        //Path dir = Path.of(path);
        Path file = Path.of(pathName);
        Files.deleteIfExists(file);
        //Files.deleteIfExists(dir);
    }

    public final String getTableName() {
        return tableName;
    }

    public final String getPathName() {
        return pathName;
    }

    //for user to use
    protected CopyInsertInfo(String tableName, String tempPath) {
        this.tableName = tableName;
        this.path = tempPath;
        this.pathName = tempPath + System.getProperty("file.separator") + tableName + ".csv";
    }

    protected CopyInsertInfo(String tableName) {
        this(tableName, defaultTempPath);
    }

    public abstract void insert(T object) throws IOException;

    public final void insert(Object... args) throws IOException {
        String[] argsStr = new String[args.length];
        for (int i = 0; i < argsStr.length; i++) {
            String s = "\"" + args[i].toString() + "\"";
            argsStr[i] = s;
        }
        printData(argsStr);
        cnt++;
    }
}
