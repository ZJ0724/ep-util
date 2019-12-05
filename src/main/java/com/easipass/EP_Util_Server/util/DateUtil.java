package com.easipass.EP_Util_Server.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 获取时间
     * */
    public static long getTime(){
        return new Date().getTime();
    }

    /**
     * 验证字符串是否能转换成指定格式的日期
     * */
    public static boolean checkParse(String parse,String date){
        DateFormat dateFormat=new SimpleDateFormat(parse);
        try {
            dateFormat.parse(date);
            return true;
        }catch (ParseException e){
            return false;
        }
    }

}
