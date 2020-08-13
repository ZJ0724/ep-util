package com.easipass.util.core;

import com.easipass.util.core.config.Port;
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
     * 项目名
     * */
    public static final String NAME = "ep-util";

    /**
     * 项目根目录
     * */
    public static final String ROOT_PATH = System.getProperty("user.dir");

    /**
     * 版本
     * */
    public static final String VERSION = FileUtil.getData(new File(ROOT_PATH, "version"));

    /**
     * 项目配置目录
     * */
    public static final String CONFIG_PATH = System.getProperty("user.home") + "/." + NAME;

    /**
     * 系统类型
     * */
    public static final SystemType SYSTEM_TYPE = System.getProperty("os.name").contains("Windows") ? SystemType.WINDOWS : System.getProperty("os.name").contains("Linux") ? SystemType.LINUX : null;

//    /**
//     * 初始化
//     * */
//    public static void init() {
//        if (VERSION == null || SYSTEM_TYPE == null) {
//            throw new ErrorException("项目异常");
//        }
//
//
//
//        // 加载port文件
//        Port.getInstance();
//    }

}