package fills.thread;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @Author ysf
 * @Description   线程池工具 //TODO
 * @Param
 * @Date 2020/12/15 20:15
 * @return
 **/

public class ExecutorUtil {

    /**
     * @Author ysf
     * @Description   创建线程池 //TODO
     * @param corePoolSize
	 * @param maximumPoolSize
	 * @param threadName
     * @Date 2020/12/16 16:22
     * @return java.util.concurrent.ExecutorService
     **/
    public static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize,int maximumPoolSize ,String threadName){
        ThreadFactory threadFactory =  new BasicThreadFactory.Builder().namingPattern(threadName).build();
        ThreadPoolExecutor executor =  new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                10000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),threadFactory);
        return executor;
    }

    /**
     * @Author ysf
     * @Description   创建线程池 //TODO
     * @Param  [corePoolSize, maximumPoolSize, threadName]
     * @Date 2020/12/17 10:20
     * @return java.util.concurrent.ExecutorService
     **/
    public static ExecutorService getExecutorService(int corePoolSize,int maximumPoolSize ,String threadName){
        ThreadFactory threadFactory =  new BasicThreadFactory.Builder().namingPattern(threadName).build();
        ExecutorService executor =  new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),threadFactory);
        return executor;
    }
}
