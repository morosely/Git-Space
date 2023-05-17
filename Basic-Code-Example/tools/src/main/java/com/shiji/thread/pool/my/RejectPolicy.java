package com.shiji.thread.pool.my;

@FunctionalInterface
// 拒绝策略
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}