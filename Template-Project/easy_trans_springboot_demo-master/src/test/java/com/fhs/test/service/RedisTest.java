/*
package com.fhs.test.service;

import com.fhs.cache.service.RedisCacheService;
import com.fhs.trans.service.impl.DictionaryTransService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisCacheService<String> redisCacheService;

    @Test
    public void testStrOpeartor() throws InterruptedException {
        redisCacheService.addStr("wl","1");
        System.out.println("add:1-" + redisCacheService.getStr("wl"));
        redisCacheService.updateStr("wl","2");
        System.out.println("add:2-" + redisCacheService.getStr("wl"));
        System.out.println("add:true-" + redisCacheService.existsStr("wl"));
        redisCacheService.removeStr("wl");
        System.out.println("add:null-" + redisCacheService.getStr("wl"));
        redisCacheService.addStr("wl","1");
        redisCacheService.expireStr("wl",1);
        Thread.sleep(2000);
        System.out.println("add:null-" + redisCacheService.getStr("wl"));
    }

}
*/
