package com.shiji.java8.optional;

import org.junit.Assert;

import java.util.Optional;

public class MyOptional {

    public static void main(String[] args) {
        //（1）使用empty()方法创建一个空的Optional对象
        Optional<String> empty = Optional.empty();

        //（2）使用of()方法创建Optional对象
        String name = "binghe";
        Optional<String> opt = Optional.of(name);


        String nullName = null;
        name = Optional.ofNullable(nullName).orElse("haitao");
        System.out.println("name = " + name);
    }
}
