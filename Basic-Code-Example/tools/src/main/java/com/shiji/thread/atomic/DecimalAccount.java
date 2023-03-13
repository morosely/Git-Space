package com.shiji.thread.atomic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public interface DecimalAccount {
    // 获取余额
    public BigDecimal getBalance();
    // 取款
    public void withdraw(BigDecimal amount);
    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    public static void runningThread(DecimalAccount account) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(BigDecimal.TEN);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(account.getBalance());
    }

    public static int updateAndGet(AtomicInteger atomicInteger, IntUnaryOperator operator){
        while (true) {
            int prev = atomicInteger.get();
            int next = operator.applyAsInt(prev);
            if (atomicInteger.compareAndSet(prev, next)) {
                //break;
                return next;
            }
        }
    }
}