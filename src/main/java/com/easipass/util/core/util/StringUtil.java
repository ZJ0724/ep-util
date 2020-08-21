package com.easipass.util.core.util;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 将字符串转为集合
     * */
    public static List<String> parsList(String data) {
        if (!(data.startsWith("[") && data.endsWith("]"))) {
            return null;
        }

        List<String> result = new ArrayList<>();
        data = data.substring(1, data.length() -1);
        String[] strings = data.split(",");

        for (String s : strings) {
            s = s.replaceAll(" ", "");
            result.add(s);
        }

        return result;
    }

    /**
     * 判断是否为null
     *
     * @param s 数据
     *
     * @return 是null返回true
     * */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    /**
     * 判断是否为null
     *
     * @param s 数据
     *
     * @return 是null返回true
     * */
    public static boolean isEmptyAll(String s) {
        return s.replaceAll(" ", "").equals("");
    }

}