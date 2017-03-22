package com.ruanyh.util.common;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;

import java.io.File;

/**
 * 系统配置
 */
public class SystemConfig {
    private static final String DEFAULT_CONFIG_FILENAME = "config.xml";
    private static Configuration CONFIG = null;
    static {
        DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
        builder.setFile(new File(DEFAULT_CONFIG_FILENAME));
        try {
            CONFIG = builder.getConfiguration(true);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取
     * @param key
     * @return
     */
    public static String get(String key) {
        return CONFIG.getString(key);
    }

}
