package com.shiji.thread.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GuardedObjectMore {
    // 标识 Guarded Object
    private int id;
    public GuardedObjectMore(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    // 结果
    private Object response;
    // 获取结果
    // timeout 表示要等待多久 2000
    public Object get(long timeout) {
        synchronized (this) {
            // 开始时间 15:00:00
            long begin = System.currentTimeMillis();
            // 经历的时间
            long passedTime = 0;
            while (response == null) {
                // 这一轮循环应该等待的时间
                long waitTime = timeout - passedTime;
                // 经历的时间超过了最大等待时间时，退出循环
                if (waitTime <= 0) {
                    break;
                }
                try {
                    this.wait(waitTime); // 虚假唤醒 15:00:01
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 求得经历时间
                passedTime = System.currentTimeMillis() - begin; // 15:00:02 1s
            }
            return response;
        }
    }
    // 产生结果
    public void complete(Object response) {
        synchronized (this) {
            // 给结果成员变量赋值
            this.response = response;
            this.notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        TimeUnit.SECONDS.sleep(1);
        //存在ConcurrentModificationException
//        for (Integer id : Mailboxes.getIds()) {
//            new Postman(id, "内容" + id).start();
//            TimeUnit.SECONDS.sleep(1);
//        }
        Set<Integer> set = Mailboxes.getIds();
        Integer[] arr = Mailboxes.getIds().toArray(new Integer[set.size()]);
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            new Postman(id, "Content_" + id).start();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}

class Mailboxes {
    private static Map<Integer, GuardedObjectMore> boxes = new Hashtable<>();
    private static int id = 1;
    // 产生唯一 id
    private static synchronized int generateId() {
        return id++;
    }
    public static GuardedObjectMore getGuardedObjectMore(int id) {
        return boxes.remove(id);
    }
    public static GuardedObjectMore createGuardedObjectMore() {
        GuardedObjectMore gom = new GuardedObjectMore(generateId());
        boxes.put(gom.getId(), gom);
        return gom;
    }
    public static Set<Integer> getIds() {
        return boxes.keySet();
    }
}

@Slf4j
class People extends Thread{
    @Override
    public void run() {
        // 收信
        GuardedObjectMore gom = Mailboxes.createGuardedObjectMore();
        log.debug("开始收信 id:{}", gom.getId());
        Object mail = gom.get(10000);
        log.debug("收到信 id:{}, 内容:{}", gom.getId(), mail);
    }
}

@Slf4j
class Postman extends Thread {
    private int id;
    private String mail;
    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }
    @Override
    public void run() {
        GuardedObjectMore gom = Mailboxes.getGuardedObjectMore(id);
        log.debug("送信 id:{}, 内容:{}", id, mail);
        gom.complete(mail);
    }
}