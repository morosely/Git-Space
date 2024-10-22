package com.deliveroo;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class UniqueIdGenerator {

    //private volatile static Set set = new HashSet<>();
    private volatile static List list = Collections.synchronizedList(new ArrayList<>());
    public static String generateUniqueId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
        String datePart = sdf.format(new Date());

        Random random = new Random();
        int randomNumber = random.nextInt(999999); // 生成六位随机数

        String uniqueId = datePart + "-" + String.format("%06d", randomNumber);
        return uniqueId;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100000; i++) {
            String uniqueId = generateUniqueId();
            list.add(uniqueId);
        }
//        CountDownLatch latch = new CountDownLatch(2);
//        for (int i = 0; i < 2; i++) {
//            new Thread(() -> {
//                for (int j = 0; j < 10000; j++) {
//                    String uniqueId = generateUniqueId();
//                    list.add(uniqueId);
//                }
//                latch.countDown();
//            }).start();
//        }
//        latch.await();
        System.out.println(list.size());
        System.out.println((new HashSet(list)).size());
    }

}
