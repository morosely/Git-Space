package com.haitao;

import com.third.config.MybatisAutoConfig;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MySpringbootMybatisApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(MySpringbootMybatisApplication.class, args);
        System.out.println("MybatisAutoConfig ===============>>>>> " + beanExists(context, MybatisAutoConfig.class));
    }


    public static boolean beanExists(ApplicationContext context,String beanName) {
        return context.containsBean(beanName);
    }

    public static <T> boolean beanExists(ApplicationContext context,Class<T> beanType) {
        try {
            context.getBean(beanType);
            return true;
        } catch (NoSuchBeanDefinitionException e) {
            return false;
        }
    }
}
