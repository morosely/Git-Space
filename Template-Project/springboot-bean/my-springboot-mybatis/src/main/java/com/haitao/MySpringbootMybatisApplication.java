package com.haitao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@ComponentScan(basePackages = "com.haitao")
public class MySpringbootMybatisApplication {

    public static void main(String[] args) {

        SpringApplication.run(MySpringbootMybatisApplication.class, args);
    }

}
