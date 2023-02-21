package com.shiji.thread.async;

/**
 * 创建回调接口的实现类
 */
public class TaskHandler implements TaskCallable<TaskResult> {

    @Override
    public TaskResult callable(TaskResult taskResult) {
        //TODO 拿到结果数据后进一步处理
        System.out.println(taskResult.toString());
        return taskResult;
    }

}
