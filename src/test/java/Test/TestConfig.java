package Test;

import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private static final Properties properties = new Properties();
    private static final String configPath = "/database.cnf";

    static {
        try (InputStream s = TestConfig.class.getResourceAsStream(configPath)) {
            properties.load(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getHostIP() {
        return properties.getProperty("host");
    }

    public static String getPort() {
        return properties.getProperty("port");
    }

    public static String getDatabase() {
        return properties.getProperty("database");
    }

    public static String getUserName() {
        return properties.getProperty("user");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }
}
