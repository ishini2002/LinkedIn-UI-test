package com.ishini.sqa.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties props = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read config.properties", e);
        }
    }

    public static String username() {
        return props.getProperty("linkedin.username", "");
    }

    public static String password() {
        return props.getProperty("linkedin.password", "");
    }
}