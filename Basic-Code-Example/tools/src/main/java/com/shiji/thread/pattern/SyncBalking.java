package com.shiji.thread.pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SyncBalking {
    // 用来表示是否已经有线程已经在执行启动了
    private volatile boolean starting;

    public void start() {
        log.info("尝试启动监控线程...");
        synchronized (this) {
            if (starting) {
                return;
            }
            starting = true;
        }
        log.info("启动监控线程...");
    }
}
