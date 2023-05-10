package com.jiade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author: JIADE
 * @description: Application
 * @date: 2023/4/4 15:30
 **/
@ComponentScan(basePackages = {"com.jiade","org.n3r.idworker"})
public class testApplication {


    public static void main(String[] args) {
        SpringApplication.run(testApplication.class,args);
    }
}
