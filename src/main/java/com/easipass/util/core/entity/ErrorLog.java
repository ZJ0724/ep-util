package com.easipass.util.core.entity;

import com.easipass.util.core.config.Project;
import com.easipass.util.core.util.FileUtil;
import java.io.File;

/**
 * 错误日志
 *
 * @author ZJ
 * */
public final class ErrorLog {

    /**
     * 错误日志文件
     * */
    private static final File FILE = new File(Project.CONFIG_PATH, "log/error.log");

    /**
     * 单例
     * */
    private static final ErrorLog ERROR_LOG = new ErrorLog();

    /**
     * 构造函数
     * */
    private ErrorLog() {}

    /**
     * 获取日志内容
     *
     * @return 日志内容
     * */
    public String getData() {
        return FileUtil.getData(FILE);
    }

    /**
     * 设置日志内容
     *
     * @param data data
     * */
    public void setData(String data) {
        FileUtil.setData(FILE, data);
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static ErrorLog getErrorLog() {
        return ERROR_LOG;
    }

}