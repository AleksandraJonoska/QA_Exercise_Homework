package com.qa1.utilities;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * reads the properties file configuration.properties
 */
public class ConfigurationReader {

    private static Properties properties;

    static {

        try {
            String path = "configuration.properties";
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);

            input.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static String getProperty(String keyName) {
        return properties.getProperty(keyName);
    }
}
/*
Configuration reader —> because we have configuration.properties file, we created a method that will help us read any data from that file.
 I created a private and static properties objects( to read from properties file) ( made it private so we are limiting access to this object), ( static to make sure it is loaded and created before everything else.
We created a static block and inside we created an object from FileInput Stream by passing the path of configuration.properties file in the constructor) Can open the file. Then we loaded the properties object with the file and then closed the file in the memory. Because it might through an exception we created a try and catch block.
After in the same class we created getProperty method(“Keyword) to read any value from the provided file. The key is hardcoded.

 */
