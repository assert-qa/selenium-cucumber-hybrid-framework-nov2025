package helpers;

import utils.LogUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public class PropertiesHelper {
    private static Properties properties;
    private static String linkFile;
    private static FileInputStream file;
    private static FileOutputStream out;
    private static String relPropertiesFilePathDefault = "src/main/resources/config.properties";

    public static Properties loadAllFiles(){
        LinkedList<String> files = new LinkedList<>();
        files.add("src/main/resources/config.properties");
        files.add("src/main/java/objects/automation_exercise.properties");
        files.add("src/main/resources/cucumber.properties");

        try {
            properties = new Properties();
            for (String filePath : files) {
                Properties tempProp = new Properties();
                linkFile = SystemHelper.getCurrentDir() + filePath;
                file = new FileInputStream(linkFile);
                tempProp.load(file);
                properties.putAll(tempProp);
            }
            LogUtils.info("Properties loaded from multiple files: " + files);
            return properties;
        }catch (IOException ioe){
            return new Properties();
        }
    }

    public static void setFile(String relPropertiesFilePath){
        properties = new Properties();
        try {
            linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePath;
            file = new FileInputStream(linkFile);
            properties.load(file);
            file.close();
        } catch (Exception e){
            LogUtils.error("Failed to load properties file: ("+relPropertiesFilePath+"): "+ e.getMessage());
        }
    }

    public static void setDefaultFile(){
        properties = new Properties();
        try {
            linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePathDefault;
            file = new FileInputStream(linkFile);
            properties.load(file);
            file.close();
        } catch (Exception e){
            LogUtils.error("Failed to load default properties file: ("+relPropertiesFilePathDefault+"): "+ e.getMessage());
        }
    }

    public static String getValue(String key){
        String value = null;
        try {
            if (file == null) {
                properties = new Properties();
                linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePathDefault;
                file = new FileInputStream(linkFile);
                properties.load(file);
                file.close();
            }
            value = properties.getProperty(key);
        }catch (IOException ioe){
            LogUtils.error("Failed to get value from properties file for key: ("+key+"): "+ ioe.getMessage());
        }
        return value;
    }

    public static void setValue(String key, String keyValue){
        try {
            if (file == null) {
                properties = new Properties();
                file = new FileInputStream(SystemHelper.getCurrentDir() + relPropertiesFilePathDefault);
                properties.load(file);
                file.close();
                out = new FileOutputStream(SystemHelper.getCurrentDir() + relPropertiesFilePathDefault);
            }
            out = new FileOutputStream(linkFile);
            LogUtils.info(linkFile);
            properties.setProperty(key, keyValue);
            properties.store(out, null);
            out.close();
        }catch (Exception e){
            LogUtils.error("Failed to set value in properties file for key: ("+key+"): "+ e.getMessage());
        }
    }
}
