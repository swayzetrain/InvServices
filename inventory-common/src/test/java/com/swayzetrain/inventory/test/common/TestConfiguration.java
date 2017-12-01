package com.swayzetrain.inventory.test.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration()
@ComponentScan({"com.swayzetrain.inventory"})
@EntityScan("com.swayzetrain.inventory")
@EnableJpaRepositories("com.swayzetrain.inventory")
@SpringBootApplication()
public class TestConfiguration{

    public static void main(String[] args) {
        SpringApplication.run(TestConfiguration.class, args);
    }
    
}