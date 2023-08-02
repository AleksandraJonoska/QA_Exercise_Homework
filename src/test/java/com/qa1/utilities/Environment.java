package com.qa1.utilities;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Environment {

    //static final (Constants)variables -- once they are initialized you can not change them

    public static final String URL;
    public static final String BASE_URL;
    public static final String DB_USERNAME;
    public static final String DB_PASSWORD;
    public static final String DB_URL;



    static{
        Properties properties = null;
        //override confi.properties else if we are not running by proving the en by default get it from properties file
        //if we provide environment variable from terminal or a maven command , use whatever we provide
        //if it is empty, if we do not provide any environment variable information, in default will read from con.properties file
        String environment = System.getProperty("environment") != null ? environment = System.getProperty("environment") : ConfigurationReader.getProperty("environment");
        //this field will get its value from configuration.properties file environment key /qa1 qa2 qa3
        //String environment = ConfigurationReader.getProperty("environment");



        try {
            //where is our file ?, path is holding that one
            String path = System.getProperty("user.dir") + "/src/test/resources/Environments/" + environment + ".properties";
            //System.getProperty("user.dir") - dynamically fining path of project( prjocet location in our computer)
            //"/src/test/resources/Environments/" -- path of environment folder
            // environment coming from properties file
            // which qa environment will load from configuration properties

            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
// dynamically change based on the environment from conf.properties because we have multiple environment
        URL = properties.getProperty("url");
        BASE_URL = properties.getProperty("base_url");
        DB_USERNAME = properties.getProperty("dbUsername");
        DB_PASSWORD = properties.getProperty("dbPassword");
        DB_URL = properties.getProperty("dbUrl");





    }

}
