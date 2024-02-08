package com.haitao;

import com.yihaitao.config.MybatisAutoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@ComponentScan(basePackages = "com.yihaitao")
public class MySpringbootMybatisApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(MySpringbootMybatisApplication.class, args);
        System.out.println("MybatisAutoConfig ===============>>>>> " + context.getBean(MybatisAutoConfig.class));
    }

}
