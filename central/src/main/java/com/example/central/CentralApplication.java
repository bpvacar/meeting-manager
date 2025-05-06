package com.example.central;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.central.repository")
@EntityScan(basePackages        = "com.example.central.model")
public class CentralApplication {
    public static void main(String[] args) {
        SpringApplication.run(CentralApplication.class, args);
    }
}
