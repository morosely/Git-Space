package com.yihaitao.definition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean//ConfigurationClassBeanDefinition 描述
    public String message(){
        return "我爱所有的美女";
    }
}
