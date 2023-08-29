package com.shiji.thread.designpattern.readwritelock.simple;

public class Data {
    private final char[] buffer;
    private final ReadWriteLock lock = new ReadWriteLock();
    public Data(int size) {
        this.buffer = new char[size];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = '*';
        }
    }
    public char[] read() throws InterruptedException {
        lock.readLock();
        try {
            return doRead();
        } finally {
            lock.readUnlock();
        }
    }
    public void write(char c) throws InterruptedException {
        lock.writeLock();
        try {
            System.out.println("【start】----------> "+Thread.currentThread().getName() + " writer start ... 【"+c+"】");
            doWrite(c);
            System.out.println("【end】----------> "+Thread.currentThread().getName() + " writer end ... 【"+c+"】");
        } finally {
            lock.writeUnlock();
        }

    }
    //(读快写慢)在doRead 方法中，我们使用 for 语句来复制数组的内容。这是为了和dowrite 方法进行比较而编写的。
    // 数组内容的复制操作通常使用 java.lang.System.arraycopy。
    private char[] doRead() {
        char[] newbuf = new char[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            newbuf[i] = buffer[i];
        }
        slowly();
        return newbuf;
    }
    private void doWrite(char c) {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = c;
            slowly();
        }
    }
    private void slowly() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }
    }
}
