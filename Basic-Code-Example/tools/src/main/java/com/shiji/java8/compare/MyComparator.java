package com.shiji.java8.compare;

import java.util.Comparator;
import java.util.TreeSet;

public class MyComparator {

    public static void main(String[] args) {
        Comparator<Integer> com = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };

        TreeSet<Integer> treeSet = new TreeSet<>(com);

        //Lambda表达式
        //Comparator<Integer> LambdaCom = (x, y) -> Integer.compare(x, y);
        TreeSet<Integer> LambdaTreeSet = new TreeSet<>((x, y) -> Integer.compare(x, y));

        LambdaTreeSet.add(3);
        LambdaTreeSet.add(1);
        LambdaTreeSet.add(2);

        System.out.println("LambdaTreeSet = " + LambdaTreeSet);

    }

}
