package com.jiade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


/**
 * @author: JIADE
 * @description: Application
 * @date: 2023/4/4 15:30
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.jiade","org.n3r.idworker"})
@EnableMongoRepositories
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
