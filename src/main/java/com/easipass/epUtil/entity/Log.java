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
     * 单例
     * */
    private final static Log log = new Log();

    /**
     * 构造函数
     * */
    private Log() {}

    /**
     * 获取单例
     * */
    public static Log getLog() {
        return Log.log;
    }

    /**
     * INFO
     * */
    public void info(String log) {
        this.outputLog("INFO", log);
    }

    /**
     * ERROR
     * */
    public void error(String log) {
        this.outputLog("ERROR", log);
    }

    /**
     * 日志格式
     */
    private String logFormat(String type, String log) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateS = simpleDateFormat.format(date);
        return "[" + dateS + "] - " + "[" + type + "] - " + log + "\n";
    }

    /**
     * 输出日志
     * */
    private void outputLog(String type, String log) {
        log = this.logFormat(type, log);

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