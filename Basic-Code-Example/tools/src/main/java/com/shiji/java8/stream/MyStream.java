package com.shiji.java8.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MyStream {

    public static void main(String[] args) {
        //将流中每一个元素都映射到map的函数中，每个元素执行这个函数，再返回
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        list.stream().map((e) -> e.toUpperCase()).forEach(System.out::printf);

        //获取Person中的每一个人得名字name，再返回一个集合
        //List<String> names = this.list.stream().map(Person :: getName).collect(Collectors.toList());
        System.out.println();
        System.out.println("==========  ==========  ==========  ==========  ========== ");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = numbers.stream().reduce(0, (x, y) -> x + y);
        System.out.println(sum);
        //Optional<Double> op = employees.stream().map(Employee::getSalary).reduce(Double::sum);
        //System.out.println(op.get());

        System.out.println("==========  ==========  ==========  ==========  ========== ");
        //使用并行流和 reduce() 方法计算整数列表的总和
        int result = numbers.parallelStream().reduce(0,(a, b) -> a + b, Integer::sum);
        System.out.println(result);
    }

    /**
     * flatMap —— 接收一个函数作为参数，将流中的每个值都换成一个流，然后把所有流连接成一个流
     */
    @Test
    public void testFlatMap() {
        StreamAPI_Test s = new StreamAPI_Test();
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        list.stream().flatMap((e) -> s.filterCharacter(e)).forEach(System.out::println);
        System.out.println(" ==========  ==========  ==========  ==========  ========== ");
        //如果使用map则需要这样写
        list.stream().map((e) -> s.filterCharacter(e)).forEach((e) -> {
            e.forEach(System.out::println);
        });
    }
}


class StreamAPI_Test {

    /**
     * 将一个字符串转换为流
     */
    public Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }

}
