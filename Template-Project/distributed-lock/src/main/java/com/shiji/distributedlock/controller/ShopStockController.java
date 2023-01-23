package com.shiji.distributedlock.controller;

import com.shiji.distributedlock.service.ShopStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class ShopStockController {

    @Autowired
    private ShopStockService service;

    private AtomicInteger requestCount = new AtomicInteger(0);

    @GetMapping("test/latch")
    public String testLatch(){
        this.service.testLatch();
        return "班长锁门。。。";
    }

    @GetMapping("test/countdown")
    public String testCountDown(){
        this.service.testCountDown();
        return "出来了一位同学";
    }

    @GetMapping("test/semaphore")
    public String testSemaphore(){
        this.service.testSemaphore();
        return "测试信号量";
    }

    @GetMapping("test/read")
    public String testRead(){
        String msg = service.testRead();
        return "测试读";
    }

    @GetMapping("test/write")
    public String testWrite(){
        String msg = service.testWrite();
        return "测试写";
    }

    @GetMapping("fairLock/{id}")
    public String fairLock(@PathVariable("id") Long id) throws InterruptedException {
        service.fairLock(id);
        return "fairLock is success";
    }

    @GetMapping("stock/deduct")
    public String deductStock(){
        //log.info("【Controller】 {} ==========>>> requestCount：{}" ,Thread.currentThread().getName(), requestCount.incrementAndGet());
        this.service.deductStock();
        return "Shop stock is deduct success";
    }

    @GetMapping("stock/reset")
    public String reset(){
        requestCount.set(0);
        //log.info("reset ==========---------->>> requestCount：{}" ,requestCount);
        return "reset requestCount is success";
    }
}
