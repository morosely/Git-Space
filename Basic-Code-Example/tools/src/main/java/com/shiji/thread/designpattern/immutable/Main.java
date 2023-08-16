package com.shiji.thread.designpattern.immutable;

public class Main {
    public static void main(String[] args) {
        /**
         * 对于适用Immutable模式的类(immutable类)我们无需再使用synchronized方法执行线程的互斥处理，
         * 因为即使不使用synchronized，也能确保安全性。虽然到此为止的内容全都是该模式的优点.
         */
        Person alice = new Person("Alice", "Alaska");
        new PrintPersonThread(alice).start();
        new PrintPersonThread(alice).start();
        new PrintPersonThread(alice).start();
    }
}
