package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    
    public static void loadFile (String filePath){
        properties = new Properties();
        try{
            FileInputStream input = new FileInputStream(filePath);
            properties.clear();
            properties.load(input);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String get(String key){
        String value = properties.getProperty(key);
        if (value == null) {
            System.err.println("Key '" + key + "' not found!");
        }
        return value;
    }
}
