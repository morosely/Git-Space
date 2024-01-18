package com.shiji.java8.anonymous;

import java.util.TreeSet;
import java.util.Comparator;
public class MyAnonymous {

    public static void main(String[] args) {
        //使用匿名内部类
        Runnable r = new Runnable(){
            @Override
            public void run(){
                System.out.println("Hello Lambda");
            }
        };

        //为Lambda表达式
        r = () -> System.out.println("Hello Lambda");


        //使用匿名内部类
        TreeSet<Integer> ts = new TreeSet<>(new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2){
                return Integer.compare(o1, o2);
            }
        });

        //为Lambda表达式
        ts = new TreeSet<>((o1, o2) -> Integer.compare(o1, o2));

    }
}
