package com.easipass.epUtil.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

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

}
