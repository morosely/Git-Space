package com.shiji.thread.completablefuture.v2;

import lombok.Data;
import static com.shiji.thread.completablefuture.Util.delay;
import static com.shiji.thread.completablefuture.Util.format;
import java.util.Random;

@Data
public class Shop {

    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];//返回一个随机生成的Discount.Code
        return name + ":" + price + ":" + code;
    }

    public double calculatePrice(String product) {
        delay();
        return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
    }
}

