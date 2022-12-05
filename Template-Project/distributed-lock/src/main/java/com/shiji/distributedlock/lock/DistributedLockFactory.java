package com.shiji.distributedlock.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DistributedLockFactory {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private String serverUuid;

    public DistributedLockFactory() {
         serverUuid = UUID.randomUUID().toString();//DistributedLockFactory单例类，serverUuid可以是服务器唯一ID
    }

    public DistributedRedisLock getRedisLock(String lockKey){
        return new DistributedRedisLock(stringRedisTemplate,lockKey,serverUuid);
    }
}
