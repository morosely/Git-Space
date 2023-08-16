package com.shiji.thread.collections;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class WordCount {

    public static final String ALPHA = "abcedfghijklmnopqrstuvwxyz";
    //创建文件
    private static void createWord(){
        int length = ALPHA.length();
        int count = 200;
        List<String> list = new ArrayList<>(length * count);
        for (int i = 0; i < length; i++) {
            char ch = ALPHA.charAt(i);
            for (int j = 0; j < count; j++) {
                list.add(String.valueOf(ch));
            }
        }
        Collections.shuffle(list);
        for (int i = 0; i < 26; i++) {
            try (PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(new FileOutputStream("D:\\tmp\\" + (i + 1) + ".txt")))) {
                String collect = list.subList(i * count, (i + 1) * count).stream().collect(Collectors.joining("\n"));
                out.print(collect);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 一是提供一个 map 集合，用来存放每个单词的计数结果，key 为单词，value 为计数
     * 二是提供一组操作，保证计数的安全性，会传递 map 集合以及 单词 List
     */
    private static <V> void demo(Supplier<Map<String,V>> supplier, BiConsumer<Map<String,V>,List<String>> consumer) {
        Map<String, V> counterMap = supplier.get();
        List<Thread> ts = new ArrayList<>();
        for (int i = 1; i <= 26; i++) {
            int idx = i;
            Thread thread = new Thread(() -> {
                List<String> words = readFromFile(idx);
                consumer.accept(counterMap, words);
            });
            ts.add(thread);
        }
        ts.forEach(t->t.start());
        ts.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();}
        });
        System.out.println(counterMap);
    }

    public static List<String> readFromFile(int i) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\tmp\\" + i +".txt")))) {
            while(true) {
                String word = in.readLine();
                if(word == null) {
                    break;
                }
                words.add(word);
            }
            return words;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //createWord();
        demo(
            // 创建 map 集合
            // 创建 ConcurrentHashMap 对不对？
            //() -> new HashMap<String, Integer>(),
            () -> new ConcurrentHashMap<String, Integer>(),
            // 进行计数
            (map, words) -> {
                for (String word : words) {
                    Integer counter = map.get(word);
                    int newValue = counter == null ? 1 : counter + 1;
                    map.put(word, newValue);
                }
            }
        );

        //参考解答1
        demo(
            () -> new ConcurrentHashMap<String, LongAdder>(),
            (map, words) -> {
                for (String word : words) {
                    // 注意不能使用 putIfAbsent，此方法返回的是上一次的 value，首次调用返回 null
                    map.computeIfAbsent(word, (key) -> new LongAdder()).increment();
                }
            }
        );

        //参考解答2
        demo(
            () -> new ConcurrentHashMap<String, Integer>(),
            (map, words) -> {
                for (String word : words) {
                    // 函数式编程，无需原子变量
                    map.merge(word, 1, Integer::sum);
                }
            }
        );
    }
}


