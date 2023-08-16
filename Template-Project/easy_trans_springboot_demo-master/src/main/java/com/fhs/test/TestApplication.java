package com.fhs.test;

import com.fhs.common.spring.SpringContextUtil;
import com.fhs.core.trans.util.ReflectUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
/*import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.mapperhelper.EntityHelper;*/

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 请看test包下的测试用例代码
 */
//@SpringBootApplication(exclude = { RedisAutoConfiguration.class })
@SpringBootApplication()
@EnableDiscoveryClient
//@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.fhs.test.mapper")
@MapperScan(basePackages = "com.fhs.test.mapper")
@EnableConfigurationProperties
public class TestApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(TestApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        RestTemplate restTemplate = builder.build();
        return restTemplate;
    }
}
