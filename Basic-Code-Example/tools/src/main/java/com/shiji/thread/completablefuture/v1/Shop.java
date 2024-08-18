package com.shiji.thread.completablefuture.v1;

import lombok.Data;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static com.shiji.thread.completablefuture.v1.Util.delay;

@Data
public class Shop {

    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /*
     客户端可以使用重载版本的get方法，它使用一个超时参数来避免发生这样的情况。这是一
     种值得推荐的做法，你应该尽量在你的代码中添加超时判断的逻辑，避免发生类似的问题。使用
     这种方法至少能防止程序永久地等待下去，超时发生时，程序会得到通知发生了TimeoutException。
     不过，也因为如此，你不会有机会发现计算商品价格的线程内到底发生了什么问题
     才引发了这样的失效。为了让客户端能了解商店无法提供请求商品价格的原因，你需要使用
     CompletableFuture的completeExceptionally方法将导致CompletableFuture内发生问
     题的异常抛出
     */
    public Future<Double> getPriceAsync(String product) {
        /*
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();//创建CompletableFuture对象，它会包含计算结果
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);//如果价格计算正常结束，完成Future操作并设置商品价格
            } catch (Exception ex) {
                futurePrice.completeExceptionally(ex);//否则抛出导致失败的异常，完成这次Future操作
            }
        }).start();
        return futurePrice;//无需等待还没结束的计算，直接返回Future对象
        */
        /*使用工厂方法supplyAsync创建CompletableFuture
        目前为止我们已经了解了如何通过编程创建CompletableFuture对象以及如何获取返回
        值，虽然看起来这些操作已经比较方便，但还有进一步提升的空间，CompletableFuture类自
        身提供了大量精巧的工厂方法，使用这些方法能更容易地完成整个流程，还不用担心实现的细节。
        比如，采用supplyAsync方法后，你可以用一行语句重写上述方法
        */
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

}
