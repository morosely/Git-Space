package com.shiji.java8.grammar;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

public class MyGrammar {

    public static void main(String[] args) {
        //1.语法格式一：无参，无返回值，Lambda体只有一条语句
        Runnable r = () -> System.out.println("Hello Lambda");

        //2.语法格式二：Lambda表达式需要一个参数，并且无返回值
        Consumer<String> func1 = (s) -> System.out.println(s);

        //3.语法格式三：Lambda只需要一个参数时，参数的小括号可以省略
        Consumer<String> func2 = s -> System.out.println(s);

        //4.语法格式四：Lambda需要两个参数，并且有返回值
        BinaryOperator<Integer> bo1 = (a, b) -> {
            System.out.println("函数式接口");
            return a + b;
        };

        //5.语法格式五：当Lambda体只有一条语句时，return和大括号可以省略
        BinaryOperator<Integer> bo2 = (a, b) -> a + b;

        //6.语法格式六：Lambda表达式的参数列表的数据类型可以省略不写，因为JVM编译器能够通过上下文推断出数据类型，这就是“类型推断”
        BinaryOperator<Integer> bo3 = (Integer a, Integer b) -> {
            return a + b;
        };
    }

    @Test
    public void test1(){
        Runnable r = () -> System.out.println("Hello Lambda");
        new Thread(r).start();
    }
    @Test
    public void test2(){
        Consumer<String> consumer = (x) -> System.out.println(x);
        consumer.accept("Hello Lambda");
    }
    @Test
    public void test3(){
        Consumer<String> consumer = x -> System.out.println(x);
        consumer.accept("Hello Lambda");
    }
    @Test
    public void test4(){
        Comparator<Integer> comparator = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };
    }
    @Test
    public void test5(){
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
    }
}
