package com.shiji.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CodeList {

    public static void main(String[] args) {
        /*UnsafeTest unsafeTest = new UnsafeTest();
        for(int i = 0; i < 10; i++) {
            new Thread(() -> {
                unsafeTest.method1();
            }, "t" + i).start();
        }*/
        /*SafeTest SafeTest = new SafeTest();
        for(int i = 0; i < 10; i++) {
            new Thread(() -> {
                SafeTest.method1();
            }, "t" + i).start();
        }*/
        UnSafeSubTest unSafeSubTest = new UnSafeSubTest();
        for(int i = 0; i < 10; i++) {
            new Thread(() -> {
                unSafeSubTest.method1();
            }, "t" + i).start();
        }
    }
}

@Slf4j
class UnsafeTest {
    List<Integer> list = new ArrayList<>();
    public void method1() {
        for (int i = 0; i < 200; i++) {
            method2();
            method3();
        }
    }
    public void method2() {
        list.add(1);
    }

    public void method3() {
        list.remove(0);
    }

}
@Slf4j
class SafeTest {
    public void method1() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            method2(list);
            method3(list);
        }
    }
    private  void method2(List<Integer> list) {
        list.add(1);
    }
    private  void method3(List<Integer> list) {
        log.info(" -----------> SafeTest method3");
        list.remove(0);
    }

}

@Slf4j
class UnSafeSubTest extends SafeTest {
    //@Override
    public void method3(List<Integer> list) {
        log.info(" -----------> Sub method3");
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}
