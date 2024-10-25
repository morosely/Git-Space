package com.deliveroo;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 单机版唯一主键生成器
 * add by yihaitao 2024-10-25
 */
public class UniqueIdGenerator {

    //private volatile static Set set = new HashSet<>();
    private volatile List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());
    private volatile ConcurrentHashMap<Long,List> synchronizedMap = new ConcurrentHashMap();
    private ThreadLocal<List<String>> threadLocalList = ThreadLocal.withInitial(() -> new ArrayList<String>());
    private static volatile AtomicInteger mark = new AtomicInteger(0);
    private static final Integer THREAD_COUNT = 1000;
    private static final Integer LOOP_COUNT = 10000;

    public String generateUniqueId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String datePart = sdf.format(new Date());
        String uniqueId = datePart + getLastChars(String.valueOf(mark.incrementAndGet()),7);
//      String uniqueId = datePart + "-" + getLastChars(String.valueOf(mark.incrementAndGet()),7);
//        System.out.println("uniqueId = " + uniqueId);
        return uniqueId;
    }
    public String getLastChars(String str,Integer lastCharLength) {
        if (str == null || str.length() < lastCharLength) {
            // 如果原始字符串为null或者长度小于lastCharLength，使用String.format补0
            return String.format("%0"+lastCharLength+"d", Integer.parseInt(str == null ? "" : str));
        } else {
            // 如果原始字符串长度大于lastCharLength，则直接取后lastCharLength位
            return str.substring(str.length() - lastCharLength);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator();
//        for (int i = 0; i < LOOP_COUNT; i++) {
//            String uniqueId = uniqueIdGenerator.generateUniqueId(Thread.currentThread().getId());
//            synchronizedList.add(uniqueId);
//        }

        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                for (int j = 0; j < LOOP_COUNT; j++) {
                    String uniqueId = uniqueIdGenerator.generateUniqueId();
                    uniqueIdGenerator.synchronizedList.add(uniqueId);
                    uniqueIdGenerator.threadLocalList.get().add(uniqueId);
                }
                System.out.println(Thread.currentThread().getName() + " : " + Thread.currentThread().getId()+" finish");
                uniqueIdGenerator.synchronizedMap.put(Thread.currentThread().getId(),uniqueIdGenerator.threadLocalList.get());
                uniqueIdGenerator.threadLocalList.remove();
                latch.countDown();
            }).start();

        }
        latch.await();

        System.out.println(uniqueIdGenerator.synchronizedList.size());
        System.out.println((new HashSet(uniqueIdGenerator.synchronizedList)).size());
        Map<String, Long> countMap = uniqueIdGenerator.synchronizedList.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() >= 2)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println(countMap.size());
        countMap.forEach((k, v) -> System.out.println(k + " ---> " + v));

        System.out.println("【countMap】 ==========> "+countMap.size());
        Map<String,Object> resultMap = new HashMap();
        AtomicInteger index = new AtomicInteger();
        countMap.forEach((k, v) -> {
            int index_mark = index.incrementAndGet();
            uniqueIdGenerator.synchronizedMap.forEach((threadId,list) -> {
                if(list.contains(k)){
                    if(resultMap.containsKey(k)){
                        resultMap.put(k,resultMap.get(k) + "|" + threadId);
                    }else{
                        resultMap.put(k,threadId);
                    }
                }
            });
            System.out.println(k + " =====================> " + resultMap.get(k));
        });
        System.out.println(resultMap);
    }

}

