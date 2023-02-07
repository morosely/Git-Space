package com.shiji.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;

public class TimeCache {

    public static void main(String[] args) throws Exception {

        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(DateUnit.SECOND.getMillis() * 5);

        timedCache.put("key1", "value1", DateUnit.SECOND.getMillis() * 10);
        timedCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 15);
        timedCache.put("key3", "value3");//默认过期(5秒)

        //timedCache.schedulePrune(DateUnit.SECOND.getMillis() * 5);
        System.out.println(DateUtil.now() + " ---> " +timedCache);
        ThreadUtil.sleep(DateUnit.SECOND.getMillis() * 20);
        System.out.println(DateUtil.now() + " ---> " +timedCache);
        timedCache.cancelPruneSchedule();
        System.out.println(DateUtil.now() + " ---> " +timedCache);
    }
}
