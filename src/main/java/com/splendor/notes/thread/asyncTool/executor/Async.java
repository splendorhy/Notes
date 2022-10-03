package com.splendor.notes.thread.asyncTool.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author splendor.s
 * @create 2022/10/3 23:28
 * @description 类入口，可以根据自己情况调整core线程的数量
 */
public class Async {

    /**
     *
     * 默认不定长线程池
     */
    private static final ThreadPoolExecutor COMMON_POOL = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    /**
     * 注意，这里是个static，也就是只能有一个线程池。用户自定义线程池时，也只能定义一个
     */
    private static ExecutorService executorService;



}
