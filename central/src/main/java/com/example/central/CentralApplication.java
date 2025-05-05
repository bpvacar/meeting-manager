package com.example.central;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CentralApplication {
    public static void main(String[] args) {
        SpringApplication.run(CentralApplication.class, args);
    }
}
