package com.easipass.util.core.entity;

import com.easipass.util.core.Project;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 打卡日志
 *
 * @author ZJ
 * */
public final class DaKaLog {

    /**
     * 日志文件
     * */
    public static final File FILE = new File(Project.CONFIG_PATH, "config/daKa.log");

    /**
     * 单例
     * */
    private static final DaKaLog DA_KA_LOG = new DaKaLog();

    /**
     * 构造函数
     * */
    private DaKaLog() {
        FileUtil.createFile(FILE);
    }

    /**
     * 获取日志
     *
     * @return 日志
     * */
    public List<String> getLogs() {
        String[] logs;
        String data = FileUtil.getData(FILE);
        if (data == null) {
            throw new ErrorException("打卡日志文件不存在");
        } else {
            logs = data.split("\n");
        }

        return new ArrayList<>(Arrays.asList(logs));
    }

    /**
     * 添加日志
     *
     * @param log log
     * */
    public void addLog(String log) {
        FileUtil.setData(FILE, "\n[" + DateUtil.getDate("yyyy-MM-dd HH:mm:ss") + "] - " + log, true);
    }

    /**
     * 设置日志
     *
     * @param log log
     * */
    public void setLog(String log) {
        FileUtil.setData(FILE, log);
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static DaKaLog getDaKaLog() {
        return DA_KA_LOG;
    }

}