package com.easipass.util.core.util;

import com.easipass.util.core.Project;
import com.easipass.util.core.SystemType;
import com.easipass.util.core.exception.ErrorException;
import java.io.IOException;

/**
 * Console工具
 *
 * @author ZJ
 * */
public class ConsoleUtil {

    /**
     * 杀掉进程
     *
     * @param processName 进程名
     * */
    public static void kill(String processName) {
        try {
            if (Project.SYSTEM_TYPE == SystemType.WINDOWS) {
                Runtime.getRuntime().exec("taskkill /im " + processName + " /F");
            }

            if (Project.SYSTEM_TYPE == SystemType.LINUX) {
                Runtime.getRuntime().exec("ps -aux | grep " + processName + " | grep -v grep | awk '{print $2}' | xargs kill -9");
            }
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 设置777权限
     *
     * @param path 文件路径
     * */
    public static void setChmod777(String path) {
        if (Project.SYSTEM_TYPE == SystemType.LINUX) {
            try {
                Runtime.getRuntime().exec("chmod 777 " + path);
            } catch (IOException e) {
                throw new ErrorException(e.getMessage());
            }
        }
    }

}