package com.example.fileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestFileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(FileServiceApplication::main).with(TestFileServiceApplication.class).run(args);
    }

}
