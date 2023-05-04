package com.shiji.thread.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 字段更新器
 * AtomicReferenceFieldUpdater // 域 字段
 * AtomicIntegerFieldUpdater
 * AtomicLongFieldUpdater
 */
public class AtomicFieldUpdaterTest {

    private volatile int field;
    public static void main(String[] args) {
        AtomicIntegerFieldUpdater fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(AtomicFieldUpdaterTest.class, "field");
        AtomicFieldUpdaterTest test = new AtomicFieldUpdaterTest();
        fieldUpdater.compareAndSet(test, 0, 10);
        // 修改成功 field = 10
        System.out.println(test.field);
        // 修改成功 field = 20
        fieldUpdater.compareAndSet(test, 10, 20);
        System.out.println(test.field);
        // 修改失败 field = 20
        fieldUpdater.compareAndSet(test, 10, 30);
        System.out.println(test.field);
    }
}
