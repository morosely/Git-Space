package com.example.myi18n.config;

import com.example.myi18n.common.threadpool.ThreadPoolComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class LogisticsConfiguration {
    /***
     * 适合处理IO密集型任务
     * @name: getFullIOExecutorServic
     * @return: java.util.concurrent.ExecutorService
     * @since: 2020/6/19 17:58
     * @author: zhangweimin
     */
    @Bean(name = "fullIOThreadPool")
    public ExecutorService getFullIOExecutorService() {
        return new ThreadPoolComponent().getFullIOExecutorService(5, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(80), new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
