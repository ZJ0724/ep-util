package com.easipass.epUtil.util;

import com.easipass.epUtil.exception.ErrorException;

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

}