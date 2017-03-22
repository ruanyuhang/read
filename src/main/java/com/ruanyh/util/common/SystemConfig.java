package com.ruanyh.util.common;

import java.io.IOException;
import java.util.Properties;

/**
 *
 */
public class SystemConfig {
    private static final String PROPERTIES_FILENAME = "system-config.properties";
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(SystemConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 私有的构造方法,不允许实例化
     */
    private SystemConfig() {
    }

    /**
     * 获取
     * @param key
     * @return
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }

}
