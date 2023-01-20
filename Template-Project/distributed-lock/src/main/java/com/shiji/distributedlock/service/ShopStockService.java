package com.shiji.distributedlock.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shiji.distributedlock.lock.DistributedLockFactory;
import com.shiji.distributedlock.lock.DistributedRedisLock;
import com.shiji.distributedlock.mapper.ShopStockModelMapper;
import com.shiji.distributedlock.model.ShopStockModel;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS) //多实例 TARGET_CLASS:CJLIB 类代理 INTERFACES：JDK代码 接口代理
public class ShopStockService {

    //private ShopStockModel shopStockModel = new ShopStockModel();
    @Autowired
    private ShopStockModelMapper shopStockMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DistributedLockFactory factory;
    @Autowired
    private RedissonClient redissonClient;

    // 读写锁（ReadWriteLock）
    public void deductStock() {
        RReadWriteLock rwlock = redissonClient.getReadWriteLock("anyRWLock");
        // 最常见的使用方法
        rwlock.readLock().lock();
        // 或
        rwlock.writeLock().lock();

        // 10秒钟以后自动解锁
        // 无需调用unlock方法手动解锁
        rwlock.readLock().lock(10, TimeUnit.SECONDS);
        // 或
        rwlock.writeLock().lock(10, TimeUnit.SECONDS);

        // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
        //boolean res = rwlock.readLock().tryLock(100, 10, TimeUnit.SECONDS);
        // 或
        //boolean res = rwlock.writeLock().tryLock(100, 10, TimeUnit.SECONDS);
        lock.unlock();
    }

    public String testRead() {
        RReadWriteLock rwLock = this.redissonClient.getReadWriteLock("rwLock");
        rwLock.readLock().lock(10, TimeUnit.SECONDS);
        System.out.println("测试读锁。。。。");
        // rwLock.readLock().unlock();
        return null;
    }

    public String testWrite() {
        RReadWriteLock rwLock = this.redissonClient.getReadWriteLock("rwLock");
        rwLock.writeLock().lock(10, TimeUnit.SECONDS);
        System.out.println("测试写锁。。。。");
        // rwLock.writeLock().unlock();
        return null;
    }


    //redisson 公平锁（Fair Lock）
    public void fairLock(Long id) throws InterruptedException {
        RLock fairLock = redissonClient.getFairLock("fairLock");
        //RLock fairLock = redissonClient.getLock("lock"); nginx造成超时重发，需要配置三个参数：proxy_connect_timeout、proxy_read_timeout、proxy_send_timeout
        // 最常见的使用方法
        fairLock.lock();
        TimeUnit.SECONDS.sleep(10);
        System.out.println("==========> id : " + id);
        // 10秒钟以后自动解锁
        // 无需调用unlock方法手动解锁
        //fairLock.lock(10, TimeUnit.SECONDS);

        // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
        //boolean res = fairLock.tryLock(100, 10, TimeUnit.SECONDS);
        fairLock.unlock();
    }

    //redisson 可重入锁（Reentrant Lock）
    public void deductStock8(){
        RLock lock = redissonClient.getLock("lock");
        lock.lock();
        try {
            //1.查询库存
            String totalStock = stringRedisTemplate.opsForValue().get("totalStock").toString();
            log.info("{} ==========>>> totalStock：{}",Thread.currentThread().getName(),totalStock);
            //2.判断库存是否充足
            if(totalStock != null && totalStock.trim().length() > 0){
                Integer stock = Integer.valueOf(totalStock);
                if(stock > 0){
                    //3.扣减库存
                    stringRedisTemplate.opsForValue().set("totalStock",String.valueOf(--stock));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //redis分布式锁 lua脚本加锁解锁
    public void deductStock7(){
        DistributedRedisLock redisLock = factory.getRedisLock("lock");
        redisLock.lock();
        try {
            //1.查询库存
            String totalStock = stringRedisTemplate.opsForValue().get("totalStock").toString();
            log.info("{} ==========>>> totalStock：{}",Thread.currentThread().getName(),totalStock);
            //2.判断库存是否充足
            if(totalStock != null && totalStock.trim().length() > 0){
                Integer stock = Integer.valueOf(totalStock);
                if(stock > 0){
                    //3.扣减库存
                    stringRedisTemplate.opsForValue().set("totalStock",String.valueOf(--stock));
                }
            }
            //addCent();
            //TimeUnit.SECONDS.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisLock.unlock();
        }
    }

    public void addCent(){
        DistributedRedisLock lock = factory.getRedisLock("lock");
        lock.lock();
        //log.info("=====》》》》》 重入锁测试");
        lock.unlock();
    }

    //redis分布式锁1.加锁：setnx 2.解锁：del 3.重试：递归
    public void deductStock6(){
        String uuid = UUID.randomUUID().toString();
        //（优化代码）递归重试 -> 循环重试获取锁
        //while(!stringRedisTemplate.opsForValue().setIfAbsent("lock","1")){
        //(锁和过期时间需要原子性)
        while(!stringRedisTemplate.opsForValue().setIfAbsent("lock",uuid,5,TimeUnit.SECONDS)){
            //休眠：竞争压力减小
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            //防止如果redis客户端程序从redis服务中获取到锁后立马宕机,造成死锁。设置锁的过期时间
            //stringRedisTemplate.expire("lock",30,TimeUnit.SECONDS);
            //1.查询库存
            String totalStock = stringRedisTemplate.opsForValue().get("totalStock").toString();
            log.info("{} ==========>>> totalStock：{}",Thread.currentThread().getName(),totalStock);
            //2.判断库存是否充足
            if(totalStock != null && totalStock.trim().length() > 0){
                Integer stock = Integer.valueOf(totalStock);
                if(stock > 0){
                    //3.扣减库存
                    stringRedisTemplate.opsForValue().set("totalStock",String.valueOf(--stock));
                }
            }
        } finally {
            //2.解锁
            //先判断是否是自己的锁，再解锁
            /*if(uuid.equals(stringRedisTemplate.opsForValue().get("lock"))){//存在第一个请求正执行此处，该锁超时已经释放（这个时刻第二请求过来获取了锁）代码往下执行，释放了第二请求的锁
                stringRedisTemplate.delete("lock");
            }*/
            //使用lua脚本解决防误删除原子性问题
            String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
            Boolean result = this.stringRedisTemplate.execute(new DefaultRedisScript<>(script,Boolean.class), Arrays.asList("lock"),uuid);
            log.info("{} ==========>>> result：{}",Thread.currentThread().getName(),result);
        }


        /*//加锁
        if(stringRedisTemplate.opsForValue().setIfAbsent("lock","1")){
            try {
                //1.查询库存
                String totalStock = stringRedisTemplate.opsForValue().get("totalStock").toString();
                log.info("{} ==========>>> totalStock：{}",Thread.currentThread().getName(),totalStock);
                //2.判断库存是否充足
                if(totalStock != null && totalStock.trim().length() > 0){
                    Integer stock = Integer.valueOf(totalStock);
                    if(stock > 0){
                        //3.扣减库存
                        stringRedisTemplate.opsForValue().set("totalStock",String.valueOf(--stock));
                    }
                }
            } finally {
                //2.解锁
                stringRedisTemplate.delete("lock");
            }
        //加锁失败，重试(递归)
        }else{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            deductStock();
        }*/
    }

    //redis(参在超卖) + redis事务
    public void deductStock5(){
        /*//1.查询库存
        String totalStock = stringRedisTemplate.opsForValue().get("totalStock").toString();
        //2.判断库存是否充足
        if(totalStock != null && totalStock.trim().length() > 0){
            Integer stock = Integer.valueOf(totalStock);
            if(stock > 0){
                //3.扣减库存
                stringRedisTemplate.opsForValue().set("totalStock", String.valueOf(--stock));
            }
        }*/
    //redis事务
     this.stringRedisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                //watch
                operations.watch((K) "totalStock");
                //1.查询库存
                String totalStock = operations.opsForValue().get("totalStock").toString();
                //2.判断库存是否充足
                if(totalStock != null && totalStock.trim().length() > 0){
                    Integer stock = Integer.valueOf(totalStock);
                    if(stock > 0){
                        //multi
                        operations.multi();
                        //3.扣减库存
                        operations.opsForValue().set((K) "totalStock", (V) String.valueOf(--stock));
                        //exec 执行事务
                        List exec = operations.exec();
                        //System.out.println("results ===> " + exec);
                        //如果执行事务为空，表示执行失败，重试
                        if(exec == null || exec.size() == 0){
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            deductStock5();
                        }
                            return exec;
                    }
                }
                return null;
            }
        });
    }

    //乐观锁
    //@Transactional
    public void deductStock4(){
        //1.查询库存
        List<ShopStockModel> list = shopStockMapper.selectList(new QueryWrapper<ShopStockModel>().eq("goodsCode","10001"));
        //2.这里取第一个库存
        if(list!=null && !list.isEmpty()){
            //3.扣减库存
            ShopStockModel shopStockModel = list.get(0);
            if(shopStockModel.getTotalStock() > 1) {
                Long totalStock = shopStockModel.getTotalStock();
                shopStockModel.setTotalStock(totalStock - 1);
                Integer version = shopStockModel.getVersion();
                shopStockModel.setVersion(version + 1);
                Integer updateCount = shopStockMapper.update(shopStockModel, new UpdateWrapper<ShopStockModel>().eq("id", shopStockModel.getId()).eq("version", version));
                System.out.println(String.format("%s =====> totalStockL:%d , version:%d , updateCount:%d",Thread.currentThread().getName(),totalStock,version,updateCount));
                if (updateCount == 0) {
                    //如果更新失败，进行重试
                    deductStock4();
                }
            }
        }
    }

    //悲观锁
    @Transactional
    public void deductStock3(){
        //System.out.println("Thead Name："+Thread.currentThread().getName() + " --- Object HashCode："+this.hashCode());
        //1.查询库存
        List<ShopStockModel> list = shopStockMapper.queryShopStock("10001");
        //2.这里取第一个库存
        if(list!=null && !list.isEmpty()){
            //3.扣减库存
            ShopStockModel shopStockModel = list.get(0);
            if(shopStockModel.getTotalStock() > 1) {
                Long totalStock = shopStockModel.getTotalStock();
                shopStockModel.setTotalStock(totalStock - 1);
                shopStockMapper.updateById(shopStockModel);
            }
        }
    }

    //一个SQL
    //@Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void deductStock2(){
        //解决:1.多例模式;2.事务;3.集群部署
        Integer count = shopStockMapper.updateStock(1,"10001");
        System.out.println("count = " + count);
        /**
        lock.lock();
        try{
            //System.out.println("Thead Name："+Thread.currentThread().getName());
            //System.out.println("Object HashCode："+this.hashCode());
            //update insert delete 写操作本身就加锁
            //1,2,3这个步骤等价于一个SQL:update shopStock set totalStock = totalStock - 1 where goodsCode = 'totalStock' and totalStock >= 1;
            //1.查询库存
            ShopStockModel shopStockModel = shopStockMapper.selectOne(new QueryWrapper<ShopStockModel>().eq("goodsCode","10001"));
            //2.判断库存是否超卖
            if(shopStockModel != null && shopStockModel.getTotalStock() > 0){
                shopStockModel.setTotalStock(shopStockModel.getTotalStock() - 1);
                System.out.println("库存量:"+ shopStockModel.getTotalStock());
                //3.更新库存到数据库
                shopStockMapper.updateById(shopStockModel);
            }
        }finally {
            lock.unlock();
        }
         */
    }

    //JVM锁：单实例
    private ReentrantLock lock = new ReentrantLock();
    public void deductStock1() {
        lock.lock();
        try {
            //System.out.println("Thead Name："+Thread.currentThread().getName());
            //System.out.println("Object HashCode："+this.hashCode());
            //1.查询库存
            ShopStockModel shopStockModel = shopStockMapper.selectOne(new QueryWrapper<ShopStockModel>().eq("goodsCode", "10001"));
            //2.判断库存是否超卖
            if (shopStockModel != null && shopStockModel.getTotalStock() > 0) {
                shopStockModel.setTotalStock(shopStockModel.getTotalStock() - 1);
                System.out.println("库存量:" + shopStockModel.getTotalStock());
                //3.更新库存到数据库
                shopStockMapper.updateById(shopStockModel);
            }
        } finally {
            lock.unlock();
        }
    }
}
