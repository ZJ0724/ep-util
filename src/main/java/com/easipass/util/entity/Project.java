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
    private static final String PROJECT_ROOT_PATH = System.getProperty("user.dir");

    /**
     * application.properties
     * */
    private static final String APPLICATION_PROPERTIES_PATH = PROJECT_ROOT_PATH + "/" + "config/application.properties";

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
        return FileUtil.getData(new File(PROJECT_ROOT_PATH, "version"));
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
     * 获取项目根目录
     *
     * @return 项目根目录
     * */
    public String getProjectRootPath() {
        return PROJECT_ROOT_PATH;
    }

    /**
     * 获取application.properties路径
     *
     * @return application.properties路径
     * */
    public String getApplicationPropertiesPath() {
        return APPLICATION_PROPERTIES_PATH;
    }

}