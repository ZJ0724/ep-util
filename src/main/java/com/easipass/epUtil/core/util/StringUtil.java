package com.easipass.epUtil.core.util;

/**
 * string工具类
 *
 * @author ZJ
 * */
public final class StringUtil {

    /**
     * 连接字符串
     *
     * @param a 第一个字符串
     * @param b 第二个字符串
     *
     * @return 连接后的字符串
     * */
    public static String append(String a, String b) {
        if (a == null) {
            a = "";
        }
        if (b == null) {
            b = "";
        }

        return a + b;
    }

}