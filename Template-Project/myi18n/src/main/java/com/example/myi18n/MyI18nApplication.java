package com.example.myi18n;

import com.example.myi18n.common.base.EnableTranslation;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableTranslation
@EnableTransactionManagement
@MapperScan("com.example.myi18n.dao")
public class MyI18nApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyI18nApplication.class, args);
    }

}
