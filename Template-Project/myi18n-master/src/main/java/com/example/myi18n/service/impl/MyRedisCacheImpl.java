package com.example.myi18n.service.impl;

import com.example.myi18n.common.contants.RedisKeyContants;
import com.example.myi18n.config.LogisticsConfiguration;
import com.example.myi18n.entity.I18nAllocate;
import com.example.myi18n.service.I18nAllocateService;
import com.example.myi18n.service.MyRedisCache;
import com.example.myi18n.service.redis.RedisService;
import com.example.myi18n.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Service
public class MyRedisCacheImpl implements MyRedisCache {
    @Autowired
    private LogisticsConfiguration fullIOThreadPool ;
    @Autowired
    private RedisService redisService;
    @Autowired
    private I18nAllocateService i18nAllocateService;
    @Override
    public boolean i18nRefresh() {
        // 利用闭锁保证两个线程都执行完毕后返回
        final CountDownLatch countDownLatch = new CountDownLatch(2);

        // 前端国际化数据，多线程处理
        fullIOThreadPool.getFullIOExecutorService().execute(() -> {
            try {
                // 构建前端所需的数据格式
                Map<String, Object> languageMap = i18nAllocateService.buildLangToWeb();
                // 保存前端国际化部分数据 Map
                redisService.set(RedisKeyContants.LANGUAGE_ZONE, JsonUtils.toJSON(languageMap));
            } finally {
                countDownLatch.countDown();
            }
        });


        // 后端国际化数据，多线程处理
        fullIOThreadPool.getFullIOExecutorService().execute(() -> {
            try {
                // 获取后端所需的数据列表
                List<I18nAllocate> languageList = i18nAllocateService.buildLangToJava();
                // 保存后端国际化部分数据 List
                redisService.set(RedisKeyContants.LANGUAGE_JAVA, JsonUtils.toJSON(languageList));
            } finally {
                countDownLatch.countDown();
            }
        });
        // 闭锁阻塞
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
