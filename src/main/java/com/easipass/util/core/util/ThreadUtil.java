package com.easipass.util.core.util;

import com.easipass.util.core.exception.ErrorException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程工具
 * */
public class ThreadUtil {

    /**
     * 线程休眠
     *
     * @param millisecond 毫秒
     * */
    public static void sleep(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 获取一个线程池
     *
     * @param num 线程数量
     *
     * @return 线程池
     * */
    public static ThreadPoolExecutor getThreadPoolExecutor(int num) {
        return new ThreadPoolExecutor(num, num, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
    }

}