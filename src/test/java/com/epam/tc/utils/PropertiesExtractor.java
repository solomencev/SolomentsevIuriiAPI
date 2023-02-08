package com.epam.tc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesExtractor {

    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";
    protected static String apiKey;
    protected static String apiToken;

    public static Properties getProperties() {

        Properties properties = new Properties();

        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
            apiKey = properties.getProperty("key");
            apiToken = properties.getProperty("token");
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static String getApiKeyFromPropertiesExtractor() {
        return getProperties().getProperty("apiKey");
    }

    public static String getApiTokenFromPropertiesExtractor() {
        return getProperties().getProperty("apiToken");
    }

}
