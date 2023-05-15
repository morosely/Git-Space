package com.shiji.thread.pattern;

import com.shiji.thread.atomic.DecimalAccount;
import com.shiji.thread.unsafe.UnsafeAccessor;
import sun.misc.Unsafe;

import java.math.BigDecimal;

/**
 * 使用自定义的 AtomicData 实现之前线程安全的原子整数 Account 实现
 */
public class AtomicData implements DecimalAccount {

    public static void main(String[] args) {
        AtomicData atomicData = new AtomicData(new BigDecimal(10000));
        DecimalAccount.runningThread(atomicData);
    }

    private volatile BigDecimal data;
    static final Unsafe unsafe;
    static final long DATA_OFFSET;

    static {
        unsafe = UnsafeAccessor.getUnsafe();
        try {
            // data 属性在 DataContainer 对象中的偏移量，用于 Unsafe 直接访问该属性
            DATA_OFFSET = unsafe.objectFieldOffset(AtomicData.class.getDeclaredField("data"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    public AtomicData(BigDecimal data) {
        this.data = data;
    }

    public void decrease(BigDecimal amount) {
        while(true) {
            // 获取共享变量旧值，可以在这一行加入断点，修改 data 调试来加深理解
            BigDecimal oldValue = data;
            // cas 尝试修改 data 为 旧值 + amount，如果期间旧值被别的线程改了，返回 false
            if (unsafe.compareAndSwapObject(this, DATA_OFFSET, oldValue, oldValue.subtract(amount))) {
                return;
            }
        }
    }

//    public BigDecimal getData() {
//        return data;
//    }

    @Override
    public BigDecimal getBalance() {
        return data;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        decrease(amount);
    }
}
