package com.shiji.thread.future;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
/*        Shop shop = new Shop();
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
        System.out.println("==========>>> 系统可用的处理器数量: "+Runtime.getRuntime().availableProcessors());
        //System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12");//来设置并行流使用的线程数的并行度。
        long start2 = System.nanoTime();
        System.out.println(Shop.findPrice_02("ABC"));
        System.out.println("==========>>> 并行流-阻塞式 用时 " + (System.nanoTime() - start2) / 1000000 + " ms");//6倍的提升，并行！

        long start3 = System.nanoTime();
        System.out.println(Shop.findPrice_03("ABC"));
        System.out.println("==========>>> 流-非阻塞式1 用时 " + (System.nanoTime() - start3) / 1000000 + " ms");//用时非常短，因为还没有算出来就会返回Future列表

        long start4 = System.nanoTime();
        System.out.println(Shop.findPrice_04("ABC"));
        System.out.println("==========>>> 流-非阻塞式2 用时 " + (System.nanoTime() - start4) / 1000000 + " ms");//发现结果和并行流不相上下！

        //原因：其内部原理都是使用了相同的线程池，并且核心线程数大小等于当前可用的CPU核心数量；但是——CompletableFuture的优势是这个数量可以自己配置以满足实际需要，而并行流不行！
        long start5 = System.nanoTime();
        System.out.println(Shop.findPrice_05("ABC"));
        System.out.println("==========>>> 流-非阻塞式2(自定义线程池) 用时 " + (System.nanoTime() - start5) / 1000000 + " ms");//成倍的提升（4倍，多4倍的核心线程数）-改进的异步CompletableFuture！*/

        DiscountShop discountShop = new DiscountShop("ABC");
        long start6 = System.nanoTime();
        List<String> pricesSequential = discountShop.findPricesSequential();
        System.out.println("==========>>> 串行支持 用时 " + (System.nanoTime() - start6) / 1000000 + " ms");

        long start7 = System.nanoTime();
        List<String> pricesParallel = discountShop.findPricesParallel();
        System.out.println("==========>>> 并行流的方式 用时 " + (System.nanoTime() - start7) / 1000000 + " ms");

        long start8 = System.nanoTime();
        List<String> pricesFuture = discountShop.findPricesFuture();
        System.out.println("==========>>> 构造同步和异步操作 用时 " + (System.nanoTime() - start7) / 1000000 + " ms");
    }
}
