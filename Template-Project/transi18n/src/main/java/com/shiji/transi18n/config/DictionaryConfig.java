package com.shiji.transi18n.config;

import com.fhs.trans.service.impl.DictionaryTransService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DictionaryConfig implements InitializingBean {

    //注入字典翻译服务
    @Autowired
    private DictionaryTransService dictionaryTransService;

    @Override
    public void afterPropertiesSet() throws Exception {
        //将字典缓存刷新到翻译服务中
        Map<String,String> transMap = new HashMap<>();
        transMap.put("0","男");
        transMap.put("1","女");
        dictionaryTransService.refreshCache("sex",transMap);
    }

}
