package com.shiji.thread.future;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
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


    // 构造同步和异步操作
    public List<String> findPricesFuture() {
        Executor executor = Executors.newFixedThreadPool(discountShops.size());
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
