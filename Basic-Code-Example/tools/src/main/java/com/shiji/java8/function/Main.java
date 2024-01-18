package com.shiji.java8.function;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {

    public String handlerString(MyFunc<String> myFunc, String str){
        return myFunc.getValue(str);
    }
    @Test
    public void test1(){
        String str = handlerString((s) -> s.toUpperCase(), "binghe");
        System.out.println(str);
    }

    @Test
    public void test2(){
        String str = handlerString((s) -> s.substring(0,4), "binghe");
        System.out.println(str);
    }

    public void operate(Long num1, Long num2, MyFuncTR<Long, Long> myFunc){
        System.out.println(myFunc.getValue(num1, num2));
    }

    @Test
    public void test3(){
        operate(100L, 200L, (x, y) -> x + y);
    }

    //Consumer接口是消费性接口，无返回值
    public void handlerConsumer(Integer number, Consumer<Integer> consumer){
        consumer.accept(number);
    }
    @Test
    public void test4(){
        this.handlerConsumer(10000, (i) -> System.out.println(i));
    }

    //Supplier接口是供给型接口，有返回值
    public List<Integer> getNumberList(int num, Supplier<Integer> supplier){
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < num; i++){
            list.add(supplier.get());
        }
        return list;
    }
    @Test
    public void test5(){
        List<Integer> numberList = this.getNumberList(10, () -> new Random().nextInt(100));
        numberList.stream().forEach(System.out::println);
    }

    //Function接口是函数型接口，有返回值
    public String handlerString(String str, Function<String, String> func){
        return func.apply(str);
    }
    @Test
    public void test6(){
        String str = this.handlerString("binghe", (s) -> s.toUpperCase());
        System.out.println(str);
    }

    //Predicate接口是断言型接口，返回值类型为boolean
    public List<String> filterString(List<String> list, Predicate<String> predicate)
    {
        List<String> strList = new ArrayList<>();
        for(String str : list){
            if(predicate.test(str)){
                strList.add(str);
            }
        }
        return strList;
    }
    @Test
    public void test7(){
        List<String> list = Arrays.asList("Hello", "Lambda", "binghe", "lyz","World");
        List<String> strList = this.filterString(list, (s) -> s.length() >= 5);
        strList.stream().forEach(System.out::println);
    }
}
