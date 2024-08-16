package com.shiji.thread.future;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {

    String shopName;

    public void doSomething() {
        try {
            System.out.println("主线程做其他事情。。。");
            Thread.sleep(1000);//模拟花时间去查询
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double getPrice(String productName) {//根据名字查价格，查询每个商店都需要一定时间
        try {
            Thread.sleep(1000);//模拟花时间去查询
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextDouble() * productName.charAt(0) + productName.charAt(1);//模拟价格-根据名字String随机产生
    }

    public Future<Double> getPriceAsync_01(String productName) {//异步查询价格
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();//创建CompletableFuture对象，它会包含计算结果
        new Thread(() -> {
            try {
                double price = getPrice(productName);
                futurePrice.complete(price);//如果价格计算正常结束，完成Future操作并设置商品价格
            } catch (Exception ex) {
                futurePrice.completeExceptionally(ex);//否则抛出导致失败的异常，完成这次Future操作
            }
        }).start();
        return futurePrice;//无需等待还没结束的计算，直接返回Future对象
    }

    public Future<Double> getPriceAsync_02(String productName) {//使用工厂方法创建CompletableFuture，传入一个supplier
        return CompletableFuture.supplyAsync(() -> getPrice(productName));//进一步精简代码
    }

//    static List<Shop> shops = Arrays.asList(
//            new Shop("A"), new Shop("B"), new Shop("C"),new Shop("D"));
    static List<Shop> shops = Arrays.asList(
            new Shop("A"), new Shop("B"), new Shop("C"),
            new Shop("D"), new Shop("E"), new Shop("F"),
            new Shop("G"), new Shop("H"), new Shop("I"),
            new Shop("J"), new Shop("A"), new Shop("B"),
            new Shop("C"), new Shop("D"), new Shop("E"),
            new Shop("F"), new Shop("G"), new Shop("H"),
            new Shop("I"), new Shop("J"), new Shop("G"),
            new Shop("H"), new Shop("I"), new Shop("J"));

    public static List<String> findPrice_01(String productName) {//Stream-阻塞式
        return shops.stream()
                .map(shop -> String.format("%s priceis% .2f", shop.getShopName(), shop.getPrice(productName))).collect(Collectors.toList());
    }

    public static List<String> findPrice_02(String productName) {//并行流-阻塞式
        return shops.parallelStream()
                .map(shop -> String.format("%s priceis% .2f", shop.getShopName(), shop.getPrice(productName)))
                .collect(Collectors.toList());
    }

    public static List<CompletableFuture<String>> findPrice_03(String productName) {//Stream-非阻塞式,只得到一个CompletableFuture<String>的List，这个值还没有算出来就会返回；
        return shops.stream().map(shop -> CompletableFuture.supplyAsync
                        (() -> String.format("%s priceis% .2f", shop.getShopName(), shop.getPrice(productName)))).
                collect(Collectors.toList());
    }

    public static List<String> findPrice_04(String productName) {//上面方法的补充：Stream-非阻塞式,将每个shop映射成一个关于shop的Future（这个future存放一个String类型的结果），再将Future的值取（get/join）出来，get抛异常/join不抛异常
        List<CompletableFuture<String>> futures = shops.stream().parallel()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s priceis% .2f", shop.getShopName(), shop.getPrice(productName))))
                .collect(Collectors.toList());
        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    //*下面来自定义自己的CompletableFuture的执行器；有个计算公式：让CPU满载100%，等待时间占比约100，CPU核心数6，差不多线程理论值上线600，但是只有24个商店，所以没必要这么多，24个即可；
    private static final Executor myExecutor = Executors.newFixedThreadPool(Math.min(100, shops.size()),//线程池中线程数目为100和商店数目二者中较小的一个值
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setDaemon(true);//设置t为"守护线程"，即标记当前线程为守护线程后，当前线程会陪伴到最后一个"用户进程"结束时，自动结束自身（不管有没有运行完），下面会给详细说明；
                    // 注意: 你现在正创建的是一个由守护线程构成的线程池。Java程序无法终止或者退出一个正在运行中的线程，如果最后一个线程需要很长时间运行而其他用户线程早早就结束了，所以最后剩下的那个线程会由于一直等待无法发生的事件而长时间的占用CPU资源，从而引发问题。
                    // 与此相反，如果将线程标记为守护进程，意味着程序（最后一个用户进程）退出时它也会被回收。这二者之间没有性能上的差异。
                    return t;
                }
            }
    );

    //*自定义了Executor，改进的CompletableFuture
    public static List<String> findPrice_05(String productName) {//上面方法的补充：Stream-非阻塞式,将每个shop映射成一个关于shop的Future（这个future存放一个String类型的结果），再将Future的值取（get/join）出来
        List<CompletableFuture<String>> futures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s priceis% .2f", shop.getShopName(), shop.getPrice(productName)), myExecutor)).collect(Collectors.toList());
        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

}
/*
 * 并行时使用流还是CompletableFutures？
 * 对集合进行并行计算有两种方式：要么将其转化为并行流，利用map操作，要么枚举出集合中的每一个元素，创建新的线程，在CompletableFuture内对其进行操作。后者提供了更多的灵活性，你可以调整线程池的大小，而这能帮助你确保整体的计算不会因为线程都在等待I/O而发生阻塞。
 * 使用这些API的建议：
 * 如果你进行的是计算密集型的操作，并且没有I/O，那么推荐使用Stream接口，因为实现简单，同时效率也可能是最高的。
 * 如果并行的工作单元还涉及等待I/O的操作（包括网络连接等待），那么使用CompletableFuture灵活性更好，可以依据等待/计算，或者W/C的比率设定需要使用的线程数。这种情况不使用并行流的另一个原因是，处理流的流水线中如果发生I/O等待，流的延迟特性会让我们很难判断到底什么时候触发了等待。
 */