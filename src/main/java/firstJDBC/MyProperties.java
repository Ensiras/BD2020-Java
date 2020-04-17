package firstJDBC;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyProperties {

    static Properties prop = new Properties();

    static {
        try (InputStream inputStream = MyProperties.class.getClassLoader().getResourceAsStream("database.properties")){
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }
}
