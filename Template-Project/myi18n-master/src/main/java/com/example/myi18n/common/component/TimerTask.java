package com.example.myi18n.common.component;

import com.example.myi18n.common.base.ResultVO;
import com.example.myi18n.controller.RefreshController;
import com.example.myi18n.service.MyRedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 定时器
 */
@Slf4j
@Component //使spring管理
@EnableScheduling //定时任务注解
public class TimerTask {
    @Autowired
    private MyRedisCache myRedisCache;
    private final String task_cron = "0 0 1/2 * * ?";

    /**
     * server容器加载完成就执行
     * 只会执行一次
     */
    @PostConstruct
    private void init() {
        mainEntrance();
    }

    @Scheduled(cron = task_cron)
    private void mainEntrance() {
        timerRefresh();
    }


    public void timerRefresh()  {
        try{
            log.info("定时器运行");
            boolean b = myRedisCache.i18nRefresh();
            log.info("定时器运行end； 运行结果是否成功：{}",b);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
