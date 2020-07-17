package com.easipass.epUtil.util;

/**
 * string工具类
 *
 * @author ZJ
 * */
public final class StringUtil {

    /**
     * 连接字符串
     *
     * @param strings 字符串数组
     *
     * @return 连接后的字符串
     * */
    public static String append(String... strings) {
        StringBuilder result = new StringBuilder();

        for (String s : strings) {
            if (s == null) {
                s = "";
            }

            result.append(s);
        }

        return result.toString();
    }

}