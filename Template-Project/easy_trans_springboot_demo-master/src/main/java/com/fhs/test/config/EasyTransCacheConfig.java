/*
package com.fhs.test.config;

import com.fhs.test.pojo.School;
import com.fhs.test.pojo.UserMp;
import com.fhs.trans.service.impl.RpcTransService;
import com.fhs.trans.service.impl.SimpleTransService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EasyTransCacheConfig implements InitializingBean {
    @Autowired
    private SimpleTransService simpleTransService;
    @Autowired
    private RpcTransService rpcTransService;

    @Override
    public void afterPropertiesSet() throws Exception {
       SimpleTransService.TransCacheSett cacheSett = new SimpleTransService.TransCacheSett();
        cacheSett.setCacheSeconds(10);
        cacheSett.setMaxCache(1000);
        cacheSett.setAccess(false);
        simpleTransService.setUniqueFieldCache(UserMp.class.getName(),"idNo");
        simpleTransService.setTransCache(School.class,cacheSett);
        simpleTransService.setTransCache(UserMp.class,cacheSett);
        rpcTransService.setTransCache(School.class.getName(),cacheSett);
        rpcTransService.setTransCache(UserMp.class.getName(),cacheSett);
    }
}
*/
