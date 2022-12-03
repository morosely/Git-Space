package com.shiji.distributedlock.controller;

import com.shiji.distributedlock.service.ShopStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class ShopStockController {

    @Autowired
    private ShopStockService service;

    private AtomicInteger requestCount = new AtomicInteger(0);

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
