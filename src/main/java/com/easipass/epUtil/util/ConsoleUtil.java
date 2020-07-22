package com.easipass.epUtil.util;

import com.easipass.epUtil.entity.Project;
import com.easipass.epUtil.entity.SystemType;
import com.easipass.epUtil.exception.ErrorException;
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
            Project project = Project.getInstance();

            if (project.getSystemType() == SystemType.WINDOWS) {
                Runtime.getRuntime().exec("taskkill /im " + processName + " /F");
            }

            if (project.getSystemType() == SystemType.LINUX) {
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
        if (Project.getInstance().getSystemType() == SystemType.LINUX) {
            try {
                Runtime.getRuntime().exec("chmod 777 " + path);
            } catch (IOException e) {
                throw new ErrorException(e.getMessage());
            }
        }
    }

}