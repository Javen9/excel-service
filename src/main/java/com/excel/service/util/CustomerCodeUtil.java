package com.excel.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by javen on 2018/5/15.
 */
public class CustomerCodeUtil {
    private final static Logger logger = LoggerFactory.getLogger(CustomerCodeUtil.class);

    private static String path = "/home/excel-service/data/customer-code.db";
    private static Properties properties;
    private static int maxSerial = 0;
    private static String prefix;
    private static int serialLen;
    private static String maxSerialKey = "max-serial-key";

    static {
        prefix = ConfUtil.getValue("customer-code-prefix");
        serialLen = Integer.parseInt(ConfUtil.getValue("customer-code-serial-len"));
        try {
            properties = new Properties();
            properties.load(new FileInputStream(path));
            if (properties.getProperty(maxSerialKey) != null) {
                maxSerial = Integer.parseInt(properties.getProperty(maxSerialKey));
            }
        } catch (Exception e) {
            logger.error("读取厂商客户代号文件异常", e);
        }
    }

    public static synchronized String getCode(String name) {
        if (properties.getProperty(name) != null) {
            return properties.getProperty(name);
        }
        String next = ++maxSerial + "";
        StringBuilder code = new StringBuilder(prefix);
        for (int i = 0; i < serialLen - next.length(); i++) {
            code.append("0");
        }
        code.append(next);
        try (OutputStream outputStream = new FileOutputStream(path)) {
            properties.setProperty(maxSerialKey, next);
            properties.setProperty(name, code.toString());
            properties.store(outputStream, "");
            return code.toString();
        } catch (Exception e) {
            logger.error("获取生成厂商客户代号异常", e);
        }
        return null;
    }
}
