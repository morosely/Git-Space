package com.example.myi18n.controller;

import com.example.myi18n.common.base.BaseController;
import com.example.myi18n.common.base.ResultVO;
import com.example.myi18n.common.contants.RedisKeyContants;
import com.example.myi18n.common.enums.ResultCode;
import com.example.myi18n.common.threadpool.ThreadPoolComponent;
import com.example.myi18n.config.LogisticsConfiguration;
import com.example.myi18n.entity.I18nAllocate;
import com.example.myi18n.service.I18nAllocateService;
import com.example.myi18n.service.MyRedisCache;
import com.example.myi18n.service.redis.RedisService;
import com.example.myi18n.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("Redis")
public class RefreshController extends BaseController {
    @Autowired
     private MyRedisCache myRedisCache;
     /**
     * 刷新国际化配置缓存
     * @return
     */
    @GetMapping(value = "/refresh")
    public ResultVO refresh() throws InterruptedException {
        boolean b = myRedisCache.i18nRefresh();
        if (b){
            return ResultVO.success(b);
        }
        return ResultVO.failure();
    }



}
