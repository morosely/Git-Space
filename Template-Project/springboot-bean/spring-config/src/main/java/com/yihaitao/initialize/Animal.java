package com.yihaitao.initialize;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Animal implements InitializingBean, DisposableBean {
    //PostConstructor,PreDestroy, 提供初始化方法和销毁方法的
    @PostConstruct
    public void initMethod(){
        System.out.println("【@PostConstruct】...Animal...PostConstruct..initMethod");
    }

    @PreDestroy
    public void destroyMethod(){
        System.out.println("【 @PreDestroy】...Animal...PreDestroy..destroyMethod");
    }
    //销毁方法
    @Override
    public void destroy() throws Exception {
        System.out.println("【DisposableBean】...Animal...destroy");
    }

    //初始化方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("【InitializingBean】...Animal...afterPropertiesSet");
    }
}
