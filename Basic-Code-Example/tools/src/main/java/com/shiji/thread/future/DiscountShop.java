package com.shiji.thread.future;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Data
public class DiscountShop {

    private final String name;
    private final Random random;

    public DiscountShop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    // 对商品组装报价
    public String getPrice(String product) {
        // 获取价格
        double price = calculatePrice(product);
        // 获取折扣
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        // 提供报价
        return name + ":" + price + ":" + code;
    }

    // 获取商品价格
    private double calculatePrice(String product) {
        delay();
        // 依据产品的名称，生成一个随机值作为价格
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    private void delay(){
        try {
            Thread.sleep(1000);//模拟花时间去查询
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static List<DiscountShop> discountShops = Arrays.asList(
            new DiscountShop("AAA"), new DiscountShop("BBB"), new DiscountShop("CCC"),
            new DiscountShop("DDD"), new DiscountShop("EEE"), new DiscountShop("FFF"),
            new DiscountShop("GGG"), new DiscountShop("HHH"), new DiscountShop("III"),
            new DiscountShop("JJJ"), new DiscountShop("KKK"), new DiscountShop("LLL"),
            new DiscountShop("MMM"), new DiscountShop("NNN"), new DiscountShop("OOO"),
            new DiscountShop("PPP"), new DiscountShop("QQQ"), new DiscountShop("RRR"));

    // 串行支持
    public List<String> findPricesSequential() {
        return discountShops.stream()
                .map(discountShop -> discountShop.getPrice(discountShop.getName())) // 获取价格
                .map(Quote::parse)                                   // 解析折扣
                .map(Discount::applyDiscount)                        // 折扣打折
                .collect(Collectors.toList());
    }

    // 并行流的方式:Stream底层依赖的是线程数量固定的通用线程池
    public List<String> findPricesParallel() {
        return discountShops.parallelStream()
                .map(discountShop -> discountShop.getPrice(discountShop.getName()))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    private static final Executor executor = Executors.newFixedThreadPool(discountShops.size(),//线程池中线程数目为100和商店数目二者中较小的一个值
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

    // 构造同步和异步操作
    public List<String> findPricesFuture() {

        List<CompletableFuture<String>> priceFutures =
                discountShops.stream()
                        .map(discountShop -> CompletableFuture.supplyAsync(() -> discountShop.getPrice(discountShop.getName()), executor)) // 以异步方式取得每个shop中指定产品的原始价格
                        .map(future -> future.thenApply(Quote::parse)) // 一般情况下解析操作不涉及任何远程服务，也不会进行任何I/O操作，它几乎可以在第一时间进行，所以能够采用同步操作，不会带来太多的延迟。
                        .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor))) // 使用另一个异步任务构造期望的Future，申请折扣
                        .collect(Collectors.<CompletableFuture<String>>toList());


        return priceFutures.stream().map(CompletableFuture::join)// 等待流中的所有Future执行完毕，并提取各自的返回值
                .collect(Collectors.toList());
    }

}
