package com.easipass.util.core.util;

import com.easipass.util.exception.ErrorException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateUtil {

    /**
     * 获取时间
     * */
    public static long getTime() {
        return new Date().getTime();
    }

    /**
     * 日期格式：yyyy-MM-dd"T"HH:mm:ss
     * */
    public static String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return dateFormat.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @param pattern 格式
     *
     * @return 已传入格式的形式的时间
     * */
    public static String getTime(String pattern) {
        Date date = new Date();
        DateFormat dateFormat  = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 获取当前星期
     *
     * @return 当前星期
     */
    public static String getWeek() {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E", Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取现在时间
     *
     * @return 当前时间。格式：HH:mm
     */
    public static String getNowTime() {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前日期
     *
     * @param pattern 日期格式
     *
     * @return 所指定格式的日期
     * */
    public static String getDate(String pattern) {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 将指定日期格式转换成另外一种格式
     *
     * @param date 被转换的日期
     * @param pattern1 被转换的日期格式
     * @param pattern2 要转换的日期格式
     *
     * @return 转换的日期
     * */
    public static String formatDate(String date, String pattern1, String pattern2) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern1);
        Date date1;
        try {
            date1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new ErrorException(e.getMessage());
        }

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern2);
        return simpleDateFormat1.format(date1);
    }

    /**
     * 将日期转换成yyyyMMdd
     * */
    public static String formatDateYYYYMMdd(String date) {
        if (date == null) {
            return null;
        }

        // 日期字段长度
        int length = date.length();

        if (length == 8) {
            return formatDate(date, "yyyyMMdd", "yyyyMMdd");
        }

        if (length == 10) {
            return formatDate(date, "yyyy-MM-dd", "yyyyMMdd");
        }

        return null;
    }

}