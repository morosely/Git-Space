package com.shiji.thread.designpattern.threadspecificstorage.simple2;

public class Main {
    public static void main(String[] args) {
        new ClientThread("Alice").start();
        new ClientThread("Bobby").start();
        new ClientThread("Chris").start();
    }
}
