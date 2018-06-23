package com.excel.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ConfUtil {
    private final static Logger logger = LoggerFactory.getLogger(ConfUtil.class);

    private static Properties properties;

    static {
        properties = new Properties();
        try {
            URL url = CustomerCodeUtil.class.getClassLoader().getResource("conf.properties");
            properties.load(url.openStream());
        } catch (IOException e) {
            logger.error("读取配置文件异常", e);
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}
