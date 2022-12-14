package com.changgou.files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//排除掉数据库自动加载
@EnableEurekaClient
public class FilesApplication {
    public static void main(String[] args) {
        SpringApplication.run(FilesApplication.class,args);
    }
}
