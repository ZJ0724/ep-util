package com.easipass.util.entity;

import com.easipass.util.exception.ErrorException;
import com.easipass.util.util.FileUtil;
import java.io.File;

/**
 * 项目
 *
 * @author ZJ
 * */
public final class Project {

    /**
     * 项目根目录
     * */
    private static String absolutePath = null;

    /**
     * 单例
     * */
    private static final Project PROJECT = new Project();

    /**
     * 构造函数
     * */
    private Project() {
        File file = new File(this.getConfigPath());

        if (!file.exists()) {
            boolean isOk = file.mkdirs();
            if (!isOk) {
                throw new ErrorException("创建配置文件夹失败");
            }
        }
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static Project getInstance() {
        return PROJECT;
    }

    /**
     * 获取版本
     *
     * @return 版本
     * */
    public String getVersion() {
        return FileUtil.getData(new File(absolutePath, "version"));
    }

    /**
     * 获取项目配置路径
     *
     * @return 项目配置路径
     * */
    public String getConfigPath() {
        return java.lang.System.getProperty("user.home") + "/.ep-util/";
    }

    /**
     * 获取系统类型
     *
     * @return 系统类型
     * */
    public SystemType getSystemType() {
        String result = java.lang.System.getProperty("os.name");

        if (result.contains("Windows")) {
            return SystemType.WINDOWS;
        }

        if (result.contains("Linux")) {
            return SystemType.LINUX;
        }

        throw new ErrorException("系统类型错误");
    }

    /**
     * 设置项目根目录
     *
     * @param absolutePath 路径
     * */
    public void setAbsolutePath(String absolutePath) {
        Project.absolutePath = absolutePath;
    }

    /**
     * 获取项目根目录
     *
     * @return 项目根目录
     * */
    public String getAbsolutePath() {
        return absolutePath;
    }

}