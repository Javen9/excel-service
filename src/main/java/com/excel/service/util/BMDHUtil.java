//package com.excel.service.util;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.HashSet;
//import java.util.Properties;
//import java.util.Set;
//
///**
// * Created by javen on 2018/5/15.
// */
//public class BMDHUtil {
//    private final static Logger logger = LoggerFactory.getLogger(BMDHUtil.class);
//
//    private static String path = "/home/excel-service/data/bmdh.db";
//    private static Set bmdhSet = new HashSet();
//    private static Properties properties;
//
//    static {
//        try {
//            properties = new Properties();
//            properties.load(new FileInputStream(path));
//            bmdhSet.addAll(properties.keySet());
//        } catch (Exception e) {
//            logger.error("读取部门代号文件异常", e);
//        }
//    }
//
//    public static synchronized boolean saveBHDM(String bmdh) {
//        if (bmdhSet.contains(bmdh)) {
//            return false;
//        }
//        bmdhSet.add(bmdh);
//        try (OutputStream outputStream = new FileOutputStream(path)) {
//            properties.setProperty(bmdh, bmdh);
//            properties.store(outputStream, "");
//            return true;
//        } catch (Exception e) {
//            logger.error("保存部门代号异常", e);
//        }
//        return false;
//    }
//}
