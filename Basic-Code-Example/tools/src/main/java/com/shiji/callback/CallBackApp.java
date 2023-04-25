package com.shiji.callback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CallBackApp {

	public static void main(String[] args) {
		 
        System.out.println("准备开始执行异步任务...");
 
        final Object context = "上下文信息";
 
        new CallBackTask(new CallBackBody() {
            @Override
            void execute(Object context) throws Exception {
                System.out.println("\n正在执行耗时操作...");
                System.out.println(context);
                TimeUnit.SECONDS.sleep(5);
                System.out.println("\n执行完成！");
            }
 
            void onSuccess(Object context) {
                System.out.println("\n成功后的回调函数...");
                System.out.println(context);
            }
 
            void onFailure(Object context) {
                System.out.println("\n失败后的回调函数...");
                System.out.println(context);
            }
        }).task(context);
 
        System.out.println("\n异步任务已经开始，请等待完成...");

    }
}
