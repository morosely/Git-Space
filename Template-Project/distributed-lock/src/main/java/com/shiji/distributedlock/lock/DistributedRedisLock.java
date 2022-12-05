package com.shiji.distributedlock.lock;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Data
@Slf4j
public class DistributedRedisLock implements Lock {

    private StringRedisTemplate stringRedisTemplate;
    private String lockKey;
    //private String uuid;
    //private String serverUuid;
    private String requestUniqueId;//请求线程唯一ID:uuid+线程id
    public static final Long DEFAULT_LOCK_TIME = 30l;//缺省锁表时间

    public DistributedRedisLock(StringRedisTemplate stringRedisTemplate,String lockKey,String serverUuid){
        this.stringRedisTemplate = stringRedisTemplate;
        this.lockKey = lockKey;
        //uuid = UUID.randomUUID().toString();
        //this.serverUuid = serverUuid;
        this.requestUniqueId = serverUuid + ":" + Thread.currentThread().getId();
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
        log.info(" tryLock =====> lockKey:【{}】,uuid:【{}】,time【{}】", lockKey,requestUniqueId,time);
        //while(!this.stringRedisTemplate.execute(new DefaultRedisScript<>(script,Boolean.class),Arrays.asList(lockName),uuid,String.valueOf(time))){
        //解决重入锁问题uuid ---> requestUniqueId()
        while(!this.stringRedisTemplate.execute(new DefaultRedisScript<>(script,Boolean.class),Arrays.asList(lockKey),requestUniqueId,String.valueOf(time))){
            TimeUnit.MILLISECONDS.sleep(200);
        }
        autoExpire();//自动续期
        return true;
    }

    @Override
    public void unlock() {
        log.info(" unlock ==========> lockKey:【{}】,uuid:【{}】", lockKey,requestUniqueId);
        String script = "if redis.call('hexists',KEYS[1],ARGV[1]) == 0 then return nil elseif redis.call('hincrby',KEYS[1],ARGV[1],-1) == 0 then return redis.call('del',KEYS[1]) else return 0 end";
        Long result = this.stringRedisTemplate.execute(new DefaultRedisScript<>(script,Long.class),Arrays.asList(lockKey),requestUniqueId);
        if(result == null){
            throw  new IllegalMonitorStateException("this lock doesn't belong to you!");
        }
    }

    @NotNull
    @Override
    public Condition newCondition() {
        return null;
    }

    //解决重入锁问题：分布式请求唯一ID（服务ID（单例）+ 线程ID）
//    private String requestUniqueId(){
//        return serverUuid + ":" + Thread.currentThread().getId();
//    }

    //自动续期
    private void autoExpire(){
        String script = "if redis.call('hexists',KEYS[1],ARGV[1]) == 1 then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end";
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //如果缓存中存在锁，自动续期
                if(stringRedisTemplate.execute(new DefaultRedisScript<>(script,Boolean.class),Arrays.asList(lockKey),requestUniqueId,String.valueOf(DEFAULT_LOCK_TIME))){
                    autoExpire();
                }
            }
        },DEFAULT_LOCK_TIME * 1000 / 3);
    }
}
