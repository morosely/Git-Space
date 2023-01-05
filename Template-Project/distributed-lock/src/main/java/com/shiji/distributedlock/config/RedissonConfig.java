package com.shiji.distributedlock.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RedissonConfig {

    @Autowired
    private Environment environment;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        String redisInfo = "redis://"+ environment.getProperty("spring.redis.host") + ":" + environment.getProperty("spring.redis.port");
        config.useSingleServer()
                .setAddress(redisInfo)// redis服务器地址和端口
                .setDatabase(Integer.valueOf(environment.getProperty("spring.redis.database"))) // 指定redis数据库编号
                .setPassword(environment.getProperty("spring.redis.password")) // 设置redis的密码
                .setConnectionMinimumIdleSize(10) // 连接池最小线程数
                .setConnectionPoolSize(50) // 连接池最大线程数
                .setIdleConnectionTimeout(60000) // 线程超时时间释放空闲连接：50-10=40
                .setConnectTimeout(1000) // 程序获取连接的超时时间
                .setTimeout(3000); //响应超时时间
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

}
