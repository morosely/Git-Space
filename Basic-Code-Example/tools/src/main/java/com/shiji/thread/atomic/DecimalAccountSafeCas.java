package com.shiji.thread.atomic;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class DecimalAccountSafeCas implements DecimalAccount {

    private AtomicReference<BigDecimal> ref;

    public DecimalAccountSafeCas(BigDecimal balance) {
        ref = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return ref.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while (true) {
            BigDecimal prev = ref.get();
            BigDecimal next = prev.subtract(amount);
            if (ref.compareAndSet(prev, next)) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        //DecimalAccount.runningThread(new DecimalAccountSafeCas(new BigDecimal("10000")));

        AtomicInteger atomicInteger = new AtomicInteger(30);
        System.out.println("atomicInteger = " + DecimalAccount.updateAndGet(atomicInteger, p -> p / 10));
        //System.out.println("atomicInteger = " + atomicInteger.get());
    }
}
