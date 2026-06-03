package com.TaskManagement3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.TaskManagement3.Repository")
@EntityScan(basePackages = "com.TaskManagement3.Entity")
@ComponentScan(basePackages = "com.TaskManagement3")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
