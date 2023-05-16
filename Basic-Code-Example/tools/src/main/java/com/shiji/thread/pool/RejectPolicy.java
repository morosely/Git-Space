package com.shiji.thread.pool;

@FunctionalInterface
// 拒绝策略
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}