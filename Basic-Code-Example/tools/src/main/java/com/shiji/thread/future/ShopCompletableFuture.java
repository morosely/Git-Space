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
public class ShopCompletableFuture {

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

    static List<ShopCompletableFuture> shops = Arrays.asList(
            new ShopCompletableFuture("A"), new ShopCompletableFuture("B"), new ShopCompletableFuture("C"),
            new ShopCompletableFuture("D"), new ShopCompletableFuture("E"), new ShopCompletableFuture("F"));
//    static List<ShopCompletableFuture> shops = Arrays.asList(
//            new ShopCompletableFuture("A"), new ShopCompletableFuture("B"), new ShopCompletableFuture("C"), new ShopCompletableFuture("D"), new ShopCompletableFuture("E"), new ShopCompletableFuture("F"),
//            new ShopCompletableFuture("G"), new ShopCompletableFuture("H"), new ShopCompletableFuture("I"), new ShopCompletableFuture("J"), new ShopCompletableFuture("A"), new ShopCompletableFuture("B"),
//            new ShopCompletableFuture("C"), new ShopCompletableFuture("D"), new ShopCompletableFuture("E"), new ShopCompletableFuture("F"), new ShopCompletableFuture("G"), new ShopCompletableFuture("H"),
//            new ShopCompletableFuture("I"), new ShopCompletableFuture("J"), new ShopCompletableFuture("G"), new ShopCompletableFuture("H"), new ShopCompletableFuture("I"), new ShopCompletableFuture("J"));

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


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ShopCompletableFuture shop = new ShopCompletableFuture();
        Future<Double> futurePrice = shop.getPriceAsync_01("ABC");
        //主线程做其他事情
        shop.doSomething();
        try {
            double price = futurePrice.get();//从Future对象中读取价格，如果价格未知，会发生阻塞
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        long start1 = System.nanoTime();
//        System.out.println(findPrice_01("VIVO-IQOO-5G"));
//        System.out.println("==========>>> 流-阻塞式 用时 " + (System.nanoTime() - start1) / 1000000 + " ms");//线性执行，24*一个任务的时间

        long start2 = System.nanoTime();
        System.out.println(findPrice_02("ABC"));
        System.out.println("==========>>> 并行流-阻塞式 用时 " + (System.nanoTime() - start2) / 1000000 + " ms");//6倍的提升，并行！

        long start3 = System.nanoTime();
        System.out.println(findPrice_03("ABC"));
        System.out.println("==========>>> 流-非阻塞式1 用时 " + (System.nanoTime() - start3) / 1000000 + " ms");//用时非常短，因为还没有算出来就会返回Future列表

        long start4 = System.nanoTime();
        System.out.println(findPrice_04("ABC"));
        System.out.println("==========>>> 流-非阻塞式2 用时 " + (System.nanoTime() - start4) / 1000000 + " ms");//发现结果和并行流不相上下！

        //原因：其内部原理都是使用了相同的线程池，并且核心线程数大小等于当前可用的CPU核心数量；但是——CompletableFuture的优势是这个数量可以自己配置以满足实际需要，而并行流不行！
        long start5 = System.nanoTime();
        System.out.println(findPrice_05("ABC"));
        System.out.println("==========>>> 流-非阻塞式2(自定义线程池) 用时 " + (System.nanoTime() - start5) / 1000000 + " ms");//成倍的提升（4倍，多4倍的核心线程数）-改进的异步CompletableFuture！

    }
}
