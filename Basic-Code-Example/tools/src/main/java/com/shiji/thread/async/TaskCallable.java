package com.shiji.thread.async;

/**
 * 定义回调接口
 */
public interface TaskCallable<T> {
    T callable(T t);
}