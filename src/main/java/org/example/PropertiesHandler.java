package org.example;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHandler {
    public static Properties GetProperties() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("/usr/app/application.properties");
            properties.load(fileInputStream);
            fileInputStream.close();

            // Iterate through the properties
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}