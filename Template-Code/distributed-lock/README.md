mysql
1.jvm本地锁：三种情况导致锁失效 600
    多例模式
    事务：Read Uncommitted
    集群部署

2.一个SQL语句：更新数量时做判断 2000
    update shopStock set totalStock = totalStock - #{totalStock} where goodsCode = #{goodsCode} and totalStock >= #{totalStock}
    解决: 上述三种锁失效情况
    产生问题：
        1.锁范围问题：表级锁，行级锁
        2.同一个商品有多条库存记录
        3.无法记录库存变化前后的状态

3.悲观锁：select ... for update 600
    mysql悲观锁中使用行级锁：
        1.锁的查询或者更新条件必须是索引字段
        2.查询或者更新条件必须是具体值
    产生问题：
        1.性能问题
        2.死锁问题: 对多条数据加锁时，加锁顺序要一致
        3.库存操作要统一：select...for update,普通select

4.乐观锁：时间戳，version版本号 CAS机制
    1.高并发情况下，性能极低
    2.ABA问题
    3.读写分离情况下，乐观锁不可靠
    性能：2 > 3 > 1 > 4
    场景：3 > 4 > 2 > 1

mysql总结：
    性能：一个SQL > 悲观锁 > JVM锁 > 乐观锁
    1.如果最求极致性能，业务场景简单并且不需要记录数据前后变化的情况：
        优先选择：一个SQL
    2.如果写并发量较低（多读），争抢不是很激烈的情况下优先选择：乐观锁
    3.如果写并发量较高，一搬会经常冲突，此时选择乐观锁的话，会导致业务代码不间断的重试
        优先选择：mysql悲观锁（可能造成死锁。订单1 A B 订单2 B A ）
    不推荐使用JVM本地锁

redis
1.jvm本地锁机制

2.redis乐观锁
    watch：可以监控一个或者多个key的值，如果在事务(exec)执行之前，key的值发生了变化，则取消事务执行
    multi：开启事务
    exec：执行事务

3.分布式锁：跨进程 跨服务 跨服务器
    场景：超卖现象（NOSQL） 缓存击穿
    实现方式：1.基于redis实现
            2.基于zookeeper/eted实现
            3.基于MySQL实现
    特征：
        1.独占排他使用：setnx
        2.防止死锁发生
            如果redis客户端程序从redis服务中获取到锁后立马宕机。
            解决：给锁添加过期时间：expire
        3.原子性：
            获取锁和过期时间原子性:set key value ex 30 nx
        4.防误删:解铃还需系铃人
            先判断再删除
        5.自动续期
    操作：
        1.加锁：setnx
        2.解锁：del
        3.重试：递归 循环

nginx配置
    upstream distributedLock {
    server localhost:10010;
    server localhost:10086;
    keepalive 256;
    }
    server {
        listen       80;
        server_name  localhost;
        location / {
           proxy_pass http://distributedLock;
           proxy_connect_timeout    600;
					 proxy_read_timeout       600;
					 proxy_send_timeout       600;
           proxy_http_version 1.1;
    			 proxy_set_header Connection "";
        }
    }
