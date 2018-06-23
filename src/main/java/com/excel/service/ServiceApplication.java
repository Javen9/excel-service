package com.excel.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplication(ServiceApplication.class).run(args);
    }
}