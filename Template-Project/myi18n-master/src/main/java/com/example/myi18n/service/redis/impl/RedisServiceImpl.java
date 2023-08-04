package com.example.myi18n.service.redis.impl;


import com.example.myi18n.service.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RedisServiceImpl implements RedisService {
    private final String KVS_KEY_SPLIT = "-";

    @Value("${spring.application.name}")
    private  String applicationName;
    @Value("${spring.profiles.active}")
    private  String profileActive;

    @Autowired
    private  StringRedisTemplate stringRedisTemplate;


    /**
     * stringRedisTemplate.opsForValue();　　//操作字符串
     * stringRedisTemplate.opsForHash();　　 //操作hash
     * stringRedisTemplate.opsForList();　　 //操作list
     * stringRedisTemplate.opsForSet();　　  //操作set
     * stringRedisTemplate.opsForZSet();　 　//操作有序set
     */

    /**
     * assembleCacheKey所有键的拼接
     * @param key
     * @return
     */
    private String assembleCacheKey(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return  applicationName + KVS_KEY_SPLIT + profileActive + KVS_KEY_SPLIT + key;
    }



    @Override
    public List<String> getmultiet(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        keys= keys.stream().map( key -> assembleCacheKey(key)).collect(Collectors.toList());
        return stringRedisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public String get(String key) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public List<String> lrange(String key, int start, int end) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return stringRedisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public int llen(String key) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return 0;
        }
        Long size = stringRedisTemplate.opsForList().size(key);
        return null == size ? 0 : size.intValue();
    }

    @Override
    public Integer getInt(String key) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean del(String key) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        try {
            Boolean delete = stringRedisTemplate.delete(key);
            return null != delete && delete;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delslike(String key) {
        key = assembleCacheKey(key);
        Set<String> keys = stringRedisTemplate.keys(key);
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        try {
            Long delete = stringRedisTemplate.delete(keys);
            return delete > 0 ?true:false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void set(String key, String value) {
        set(key, value, null);
    }

    @Override
    public void set(String key, String value, Integer expire) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }
        if (null != expire) {
            stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(key, value);
        }
    }


    @Override
    public Long incr(String key) {
        return incr(key, 1, null);
    }

    @Override
    public Long incr(String key, Integer expire) {
        return incr(key, 1, expire);
    }

    @Override
    public Long incr(String key, int delta, Integer expire) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Long ret = stringRedisTemplate.opsForValue().increment(key, delta);
        if (null != expire) {
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return ret;
    }

    @Override
    public Long decr(String key) {
        return incr(key, -1, null);
    }

    @Override
    public Long decr(String key, Integer expire) {
        return incr(key, -1, expire);
    }

    @Override
    public void rpush(String key, String value, Integer expire) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }
        stringRedisTemplate.opsForList().rightPush(key, value);
        if (null != expire) {
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    @Override
    public void lpush(String key, String value, Integer expire) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }
        stringRedisTemplate.opsForList().leftPush(key, value);
        if (null != expire) {
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    @Override
    public void rmpush(String key, Integer count, String value) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }
        stringRedisTemplate.opsForList().remove(key,count, value);
    }

    @Override
    public String lpop(String key) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return stringRedisTemplate.opsForList().leftPop(key);
    }

    @Override
    public void expire(String key, Integer expire) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return;
        }
        if (null != expire) {
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    @Override
    public boolean setnx(String key, Integer expire) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        Boolean ret = stringRedisTemplate.opsForValue().setIfAbsent(key, "1");
        if (null == ret || !ret) {
            return false;
        }
        if (null != expire) {
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return true;
    }

    @Override
    public boolean setnxv(String key,String value ,Integer expire) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        Boolean ret = stringRedisTemplate.opsForValue().setIfAbsent(key, value);
        if (null == ret || !ret) {
            return false;
        }
        if (null != expire) {
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return true;
    }

    @Override
    public Boolean hasKey(String key) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        Boolean result = stringRedisTemplate.hasKey(key);
        return null == result ? false : result;
    }

    @Override
    public void sadd(String key, String value, Integer expire) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }
        Long result = stringRedisTemplate.opsForSet().add(key, value);
        if (null != expire && null != result && result > 0) {
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    @Override
    public void srem(String key, String memeber) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(memeber)) {
            return;
        }
        stringRedisTemplate.opsForSet().remove(key, memeber);
    }

    @Override
    public boolean sismember(String key, String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        Boolean result = stringRedisTemplate.opsForSet().isMember(key, value);
        return null == result ? false : result;
    }

    @Override
    public Set<String> smembers(String key) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return stringRedisTemplate.opsForSet().members(key);
    }

    @Override
    public Long ssize(String key) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return stringRedisTemplate.opsForSet().size(key);
    }

    @Override
    public boolean zadd(String key, String member, double score, Integer expire) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        Boolean result = stringRedisTemplate.opsForZSet().add(key, member, score);
        if (null == result || !result) {
            return false;
        }
        if (null != expire) {
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return true;
    }

    @Override
    public long zinterstore(String key, List<String> otherKeys, String destKey, Integer expire) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return 0;
        }
        destKey = assembleCacheKey(destKey);
        if (StringUtils.isEmpty(destKey)) {
            return 0;
        }
        if (CollectionUtils.isEmpty(otherKeys)) {
            return 0;
        }
        List<String> finalOtherKeys = new ArrayList<>();
        for (String eachOtherKey : otherKeys) {
            finalOtherKeys.add(assembleCacheKey(eachOtherKey));
        }
        Long result = stringRedisTemplate.opsForZSet().intersectAndStore(key, finalOtherKeys, destKey);
        if (null == result || result <= 0) {
            return 0;
        }
        if (null != expire) {
            stringRedisTemplate.expire(destKey, expire, TimeUnit.SECONDS);
        }
        return result;
    }

    @Override
    public String zfirst(String key) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Set<String> stringSet = stringRedisTemplate.opsForZSet().range(key, 0, 0);
        return CollectionUtils.isEmpty(stringSet) ? null : stringSet.toArray()[0].toString();
    }

    @Override
    public boolean zrem(String key, String member) {
        if (StringUtils.isEmpty(member)) {
            return false;
        }
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        Long result = stringRedisTemplate.opsForZSet().remove(key, member);
        return null != result && result > 0;
    }

    @Override
    public Map<String, Double> zscan(String key) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        try {
            Cursor<ZSetOperations.TypedTuple<String>> tupleCursor = stringRedisTemplate.opsForZSet().scan(key, ScanOptions.scanOptions().build());
            Map<String, Double> result = new HashMap<>();
            while (tupleCursor.hasNext()) {
                ZSetOperations.TypedTuple<String> tuple = tupleCursor.next();
                result.put(tuple.getValue(), tuple.getScore());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Double zincrby(String key, String member, double incrScore, Integer expire) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Double score = stringRedisTemplate.opsForZSet().incrementScore(key, member, incrScore);
        if (null != expire && expire > 0) {
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return score;
    }

    @Override
    public Double zscore(String key, String member) {
        if (StringUtils.isEmpty(member)) {
            return null;
        }
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return stringRedisTemplate.opsForZSet().score(key, member);
    }

    @Override
    public void hput(String key, String hashKey, Object value, Integer expire) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return;
        }
        stringRedisTemplate.opsForHash().put(key, hashKey, value);
        if (null != expire && expire > 0) {
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    @Override
    public void hdel(String key, String hashKey) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return;
        }
        stringRedisTemplate.opsForHash().delete(key, hashKey);
    }

    @Override
    public Object hget(String key, String hashKey) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return stringRedisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public Long hsize(String key) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return stringRedisTemplate.opsForHash().size(key);
    }

    @Override
    public List<Object> hgetAll(String key) {
        key = assembleCacheKey(key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return stringRedisTemplate.opsForHash().values(key);
    }



    @Override
    public void batchInsert(List<Map<String, String>> saveList, TimeUnit unit, int timeout) {
        /* 插入多条数据 */
        stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                for (Map<String, String> needSave : saveList) {
                    stringRedisTemplate.opsForValue().set(needSave.get("key"), needSave.get("value"), timeout,unit);
                }
                return null;
            }
        });
    }



    @Override
    public List<String> batchGet(List<String> keyList) {
        /* 批量获取多条数据 */
        List<Object> objects = stringRedisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                StringRedisConnection stringRedisConnection = (StringRedisConnection) redisConnection;
                for (String key : keyList) {
                    stringRedisConnection.get(key);
                }
                return null;
            }
        });

        List<String> collect = objects.stream().map(val -> String.valueOf(val)).collect(Collectors.toList());

        return collect;
    }


}

