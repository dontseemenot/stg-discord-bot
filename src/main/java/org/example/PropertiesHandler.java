package org.example;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHandler {
    private final Properties properties;
    public PropertiesHandler() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream
                    ("/usr/app/application.properties");
                    //("application.properties");
            properties.load(fileInputStream);
            fileInputStream.close();

            // Iterate through the properties
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.properties = properties;
    }

    public String GetProperty(String value) {
        return this.properties.getProperty(value);
    }
}