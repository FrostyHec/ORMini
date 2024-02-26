package org.frostyheco.utils;

import java.io.*;

public class CloneUtils {
    /**
     * DeepClone using serializable.
     * @param obj
     * @return
     * @param <T>
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T extends Serializable> T deepClone(T obj) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (T) ois.readObject();
    }
}
