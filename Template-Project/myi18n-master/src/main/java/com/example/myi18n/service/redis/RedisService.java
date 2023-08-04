package com.example.myi18n.service.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {

    //Redis 字符串(String)

    /**
     * 模糊值再删除
     * @param key
     * @return
     */
    boolean delslike(String key);

    /**
     * 通过键来获取值
     * @param key
     * @return String
     */
    String get(String key);


    /**
     *  获取值装成Int类型
     * @param key
     * @return
     */
    Integer getInt(String key);

    /**
     * 删除一个键
     * @param key
     * @return
     */
    boolean del(String key);

    /**
     * key设置value 没有失效时间
     * @param key
     * @param value
     */
    void set(String key, String value);
    /**
     * key设置value 有失效时间
     * @param key
     * @param value
     * @param expire
     */
    void set(String key, String value, Integer expire);

    /**
     *  向key递增1
     * @param key
     * @return
     */
    Long incr(String key);

    /**
     * 向key递增1 ,设置expire失效时间
     * @param key
     * @param expire
     * @return
     */
    Long incr(String key, Integer expire);

    /**
     *  向key递增delta数值 并设置expire失效时间
     * @param key
     * @param delta
     * @param expire
     * @return
     */
    Long incr(String key, int delta, Integer expire);
    /**
     *  向key递减1
     * @param key
     * @return
     */
    Long decr(String key);
    /**
     * 向key递减1 ,设置expire失效时间
     * @param key
     * @param expire
     * @return
     */
    Long decr(String key, Integer expire);

    /**
     * 向key设置expire失效时间
     * @param key
     * @param expire
     */
    void expire(String key, Integer expire);

    /**
     *  判断key是不存在，设值为1和设置失效时间 成功返回 true
     *  判断key是存在，直接返回false
     * @param key
     * @param expire
     * @return
     */
    boolean setnx(String key, Integer expire);
    /**
     *  判断key是不存在，设值为value和设置失效时间 成功返回 true
     *  判断key是存在，直接返回false
     * @param key
     * @param expire
     * @return
     */
    boolean setnxv(String key,String value ,Integer expire);

    /**
     * 判断键是否存在
     * @param key
     * @return
     */
    Boolean hasKey(String key);
    //Redis 集合(Set)
    /**
     Redis 的 Set 是 String 类型的无序集合。集合成员是唯一的，这就意味着集合中不能出现重复的数据。
     Redis 中集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。
     集合中最大的成员数为 232 - 1 (4294967295, 每个集合可存储40多亿个成员)。
     */

    /**
     * Redis Sadd 命令将一个或多个成员元素加入到集合中，已经存在于集合的成员元素将被忽略。
     * 假如集合 key 不存在，则创建一个只包含添加的元素作成员的集合。
     * 当集合 key 不是集合类型时，返回一个错误。
     * 注意：在 Redis2.4 版本以前， SADD 只接受单个成员值。
     */
    /**
     *  设置set 不重复的值，
     *  向key插入value值 ，
     *  expire为键的失效时间
     * @param key
     * @param value
     * @param expire null 无时间
     */
    void sadd(String key, String value, Integer expire);

    /**
     *  向key键 的set 删除一个元素memeber
     * @param key
     * @param memeber
     */
    void srem(String key, String memeber);

    /**
     * set中命令判断成员元素是否是集合的成员。
     * @param key
     * @param value
     * @return
     */
    boolean sismember(String key, String value);

    /**
     * set取出key所有的数据
     * @param key
     * @return
     */
    Set<String> smembers(String key);

    /**
     * set中这个key值的数量
     * @param key
     * @return
     */
    Long ssize(String key);

    //Redis 列表(List)
    /**
     Redis列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）
     一个列表最多可以包含 232 - 1 个元素 (4294967295, 每个列表超过40亿个元素)。
     */

    /**
     *  取得key分页的数据
     * @param key list 的键
     * @param start 开始下标
     * @param end 结束下标
     * @return
     */
    List<String> lrange(String key, int start, int end);

    /**
     * list取key的下值的数量
     * @param key
     * @return
     */
    int llen(String key);

    /**
     *           开始---结尾
     * 向右添加数据 左---→右 添加数据
     * @param key list 键
     * @param value 添加到list的值
     * @param expire list 失效时间
     */
    void rpush(String key, String value, Integer expire);
    /**
     *           结尾---开始
     * 向左添加数据 右---→左 添加数据
     * @param key list 键
     * @param value 添加到list的值
     * @param expire list 失效时间
     */
    void lpush(String key, String value, Integer expire);

    /**
     *  list中这个key键下删除count个value值
     * @param key list 键
     * @param count 删除多少个值
     * @param value list 中的值
     */
    void rmpush(String key,Integer count, String value);


    /**
     *  移出并获取列表的第一个元素(下标为0的元素)
     * @param key list 键
     * @return
     */
    String lpop(String key);

    // Redis 有序集合(sorted set)
    /**
     Redis 有序集合和集合一样也是string类型元素的集合,且不允许重复的成员。
     不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
     有序集合的成员是唯一的,但分数(score)却可以重复。
     集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。 集合中最大的成员数为 232 - 1 (4294967295, 每个集合可存储40多亿个成员)。
     */

    /**
     * Redis Zadd 命令用于将一个或多个成员元素及其分数值加入到有序集当中。
     * 如果某个成员已经是有序集的成员，那么更新这个成员的分数值，并通过重新插入这个成员元素，来保证该成员在正确的位置上。
     * 分数值可以是整数值或双精度浮点数。
     * 如果有序集合 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     * 注意： 在 Redis 2.4 版本以前， ZADD 每次只能添加一个元素。
     */

    /**
     * @param key    有序集合 key
     * @param member 成员元素
     * @param score  成员的分数值
     * @return 成功与否
     */
    boolean zadd(String key, String member, double score, Integer expire);

    /**
     * Redis Zinterstore 命令计算给定的一个或多个有序集的交集，其中给定 key 的数量必须以 numkeys 参数指定，并将该交集(结果集)储存到 destination 。
     * 默认情况下，结果集中某个成员的分数值是所有给定集下该成员分数值之和
     */
    long zinterstore(String key, List<String> otherKeys, String destKey, Integer expire);


    /**
     * 获取有序集合的第一个元素，通过索引区间来实现，而不是通过分数
     * @param key
     * @return
     */
    String zfirst(String key);

    /**
     * 删除zset中指定的member值
     * @param key
     * @param member
     * @return
     */
    boolean zrem(String key, String member);

    /**
     * zset取出key中所有的数据
     * @param key
     * @return
     */
    Map<String, Double> zscan(String key);

    /**
     * 对key的zset中member进行递增或者递减incrScore值
     * @param key zset 键
     * @param member 目标
     * @param incrScore 要改变的数值
     * @param expire 失效时间
     * @return
     */
    Double zincrby(String key, String member, double incrScore, Integer expire);

    /**
     * 找到对key的zset中member值（也就是这个member分数）
     * @param key
     * @param member
     * @return
     */
    Double zscore(String key, String member);

    //Redis 哈希(Hash)

    /**
     Redis hash 是一个 string 类型的 field 和 value 的映射表，hash 特别适合用于存储对象。
     Redis 中每个 hash 可以存储 232 - 1 键值对（40多亿）。
     */

    /**
     * 添加 hash对象
     * hash一般存储对象 key 标记那个用户
     * hashKey用户下的什么信息
     * value 具体数据
     * @param key hash 的键
     * @param hashKey 目标键
     * @param value 存储的值
     * @param expire 失效时间
     */
    void hput(String key, String hashKey, Object value, Integer expire);

    /**
     * 删除 hash对象 key键 中的 hashKey
     * @param key
     * @param hashKey
     */
    void hdel(String key, String hashKey);

    /**
     * 获取 hash对象 key键 中的 hashKey具体的数据
     * @param key
     * @param hashKey
     */
    Object hget(String key, String hashKey);

    /**
     *  获取 hash对象 key键 下有多少个对象数量
     * @param key
     * @return
     */
    Long hsize(String key);

    /**
     *获 取 hash对象 key键 下有所有的对象
     * @param key
     * @return
     */
    List<Object> hgetAll(String key);

    //Redis 管道(Pipeline)

    /**
     * 这个过程称为Round trip time(简称RTT, 往返时间)，mget mset有效节约了RTT，
     * 但大部分命令（如hgetall，并没有mhgetall）不支持批量操作，需要消耗N次RTT ，
     * 这个时候需要pipeline来解决这个问题
     */

    /**
     * 管道批量插入
     * @author DuanLinpeng
     * @date 2021/01/08 15:15
     * @param saveList
     * @param unit
     * @param timeout
     * @return void
     */
    void batchInsert(List<Map<String, String>> saveList, TimeUnit unit, int timeout);

    /**
     *  batchGet管道形式（推荐，性能高，类型多）
     * @param keyList
     * @return
     */
    List<String> batchGet(List<String> keyList);

    /**
     *  multiGet批量查询（只能是str）
     * @param keys
     * @return List<String>
     */
    List<String> getmultiet(List<String> keys);


}
