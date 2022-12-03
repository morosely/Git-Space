package com.shiji.distributedlock.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Data
@Slf4j
public class DistributedRedisLock implements Lock {

    private StringRedisTemplate stringRedisTemplate;
    private String lockName;
    private String uuid;
    public static final Long DEFAULT_LOCK_TIME = 3600l;//缺省锁表时间

    public DistributedRedisLock(StringRedisTemplate stringRedisTemplate,String lockName){
        this.stringRedisTemplate = stringRedisTemplate;
        this.lockName = lockName;
        uuid = UUID.randomUUID().toString();
    }

    @Override
    public void lock() {
        this.tryLock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            return this.tryLock(DEFAULT_LOCK_TIME,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, @NotNull TimeUnit unit) throws InterruptedException {
        if(time != -1){
            time = unit.toSeconds(time);
        }
        String script = "if redis.call('exists',KEYS[1]) == 0 or redis.call('hexists',KEYS[1],ARGV[1]) == 1 then redis.call('hincrby',KEYS[1],ARGV[1],1) redis.call('expire',KEYS[1],ARGV[2]) return 1 else return 0 end";
        //如果没有获取死锁，重试
        log.info(" tryLock =====> lockName:【{}】,uuid:【{}】,time【{}】",lockName,uuid,time);
        while(!this.stringRedisTemplate.execute(new DefaultRedisScript<>(script,Boolean.class),Arrays.asList(lockName),uuid,String.valueOf(time))){
            TimeUnit.MILLISECONDS.sleep(200);
        }
        return true;
    }

    @Override
    public void unlock() {
        log.info(" unlock ==========> lockName:【{}】,uuid:【{}】",lockName,uuid);
        String script = "if redis.call('hexists',KEYS[1],ARGV[1]) == 0 then return nil elseif redis.call('hincrby',KEYS[1],ARGV[1],-1) == 0 then return redis.call('del',KEYS[1]) else return 0 end";
        Long result = this.stringRedisTemplate.execute(new DefaultRedisScript<>(script,Long.class),Arrays.asList(lockName),uuid);
        if(result == null){
            throw  new IllegalMonitorStateException("this lock doesn't belong to you!");
        }
    }

    @NotNull
    @Override
    public Condition newCondition() {
        return null;
    }
}
