package com.easipass.epUtil.entity;

import com.easipass.epUtil.config.ProjectConfig;
import com.easipass.epUtil.exception.ErrorException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    /**
     * INFO
     * */
    public static void info(String log) {
        Log.outputLog("INFO", log);
    }

    /**
     * ERROR
     * */
    public static void error(String log) {
        Log.outputLog("ERROR", log);
    }

    /**
     * 日志格式
     */
    private static String logFormat(String type, String log) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateS = simpleDateFormat.format(date);
        return "[" + dateS + "] - " + "[" + type + "] - " + log + "\n";
    }

    /**
     * 输出日志
     * */
    private static void outputLog(String type, String log) {
        log = Log.logFormat(type, log);

        System.out.print(log);

        // 日志文件不存在创建文件
        if (!ProjectConfig.LOG_FILE.getParentFile().exists()) {
            ProjectConfig.LOG_FILE.getParentFile().mkdirs();
        }

        try {
            OutputStream outputStream = new FileOutputStream(ProjectConfig.LOG_FILE, true);
            outputStream.write(log.getBytes());
            outputStream.close();
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }
    }

}