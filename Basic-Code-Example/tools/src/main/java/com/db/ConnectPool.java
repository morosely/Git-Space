package com.db;

import cn.hutool.db.AbstractDb;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectPool {
    private static final Log log = LogFactory.get();
    private static final String TABLE_NAME = "shop";
    private static final Integer THREAD_COUNT = 20;
    private static final Integer LOOP_COUNT = 100;

    private static volatile AtomicInteger SUCCESS_SUM = new AtomicInteger(0);
    private static volatile AtomicInteger FAIL_SUM = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                int successCount = 0;
                int failCount = 0;
                for (int j = 0; j < LOOP_COUNT; j++) {
                    boolean result = connetctDBCRUD();
                    if(result){
                        successCount += 1;
                    }else{
                        failCount += 1;
                    }
                }
                System.out.println(Thread.currentThread().getName() + " : " + Thread.currentThread().getId() + " successCount = " + successCount + " , failCount = " + failCount);
                SUCCESS_SUM.addAndGet(successCount);
                FAIL_SUM.addAndGet(failCount);
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println(Thread.currentThread().getName() + " : " + Thread.currentThread().getId() +" =====>>> 【Total = "+(SUCCESS_SUM.get()+FAIL_SUM.get())+"】 其中：SUCCESS_SUM = " + SUCCESS_SUM + ", FAIL_SUM = " + FAIL_SUM);

    }

    private static boolean connetctDBCRUD(){
        Connection conn = null;
        DataSource ds = DSFactory.get();
        try {
            conn = ds.getConnection();
            // 执行非查询语句，返回影响的行数
            int count = SqlExecutor.execute(conn, "UPDATE " + TABLE_NAME + " set shopSize = ? where shopId = ?", 100, "1627418439407116016");
            //log.info("============================= >>> 影响行数：{}", count);

            // 执行非查询语句，返回自增的键，如果有多个自增键，只返回第一个
            //Long generatedKey = SqlExecutor.executeForGeneratedKey(conn, "UPDATE " + TABLE_NAME + " set shopSize = ? where shopForm = ?", 100, "GMS - Store");
            //log.info("==========>>> 主键：{}", generatedKey);

            // 执行查询语句，返回实体列表，一个Entity对象表示一行的数据，Entity对象是一个继承自HashMap的对象，存储的key为字段名，value为字段值
            List<Entity> entityList = SqlExecutor.query(conn, "select * from " + TABLE_NAME + " where shopForm = ?", new EntityListHandler(), "Living Plaza");
            //log.info("============================= >>> {}", entityList.size());
            return true;
        } catch (SQLException e) {
            log.error(e, "SQL error!", log);
            return false;
        } finally {
            DbUtil.close(conn);
        }
    }
}
