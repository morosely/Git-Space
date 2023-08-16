package com.shiji.thread.designpattern.immutable.sample2;

import java.util.List;

public class ReaderThread extends Thread {
    private final List<Integer> list;
    public ReaderThread(List<Integer> list) {
        super("ReaderThread");
        this.list = list;
    }
    public void run() {
        while (true) {
            /**
             * “写”线程是显式调用add方法和 remove 方法，故可沿用示例1的代码，
             * 而“读”线程是隐式调用迭代器，故需要修改其代码。如下面这样，
             * 当执行ist 的增强 for 循环时，使用synchronized代码块同步。
             */
            synchronized (list) {
                for (int n : list) {
                    System.out.println(n);
                }
            }
        }
    }
}
