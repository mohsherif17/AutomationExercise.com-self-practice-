package Utils.DataReader;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Properties;

public class PropertyReader {
    private static final Properties properties = new Properties();

    public static void loadProperties() {
        try {
            Collection<File> propsFiles = FileUtils.listFiles(
                    new File("src/main/resources"),
                    new String[]{"properties"},
                    true
            );
            for (File file : propsFiles) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    properties.load(fis);
                }
            }
            // Merge with system properties
            properties.putAll(System.getProperties());
            System.getProperties().putAll(properties);
            System.out.println(" Properties loaded successfully from src/main/resources/");
        } catch (Exception e) {
            System.err.println(" Error loading properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            value = properties.getProperty(key);
        }
        return value != null ? value.trim() : null;
    }
}
