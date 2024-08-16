package com.shiji.thread.future;

// 折扣服务
public class Discount {

    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    // 根据折扣计算新价格
    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    // 价格打折
    private static double apply(double price, Code code) {
        delay();
        return price * (100 - code.percentage) / 100;
    }

    private static void delay(){
        try {
            Thread.sleep(1000);//模拟花时间去查询
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
