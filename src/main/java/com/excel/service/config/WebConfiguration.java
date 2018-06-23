//package com.excel.service.config;
//
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.multipart.MultipartResolver;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//import org.springframework.web.servlet.DispatcherServlet;
//
//@Configuration
//public class WebConfiguration {
//
//    @Bean
//    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
//        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
//        registration.addUrlMappings("/");
//        return registration;
//    }
//
//    @Bean
//    public MultipartResolver getMultipartResolver() {
//        CommonsMultipartResolver mr = new CommonsMultipartResolver();
//        mr.setMaxInMemorySize(1024);
//        return mr;
//    }
//
//}