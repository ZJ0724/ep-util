package com.easipass.util.core;

import com.easipass.util.core.util.FileUtil;
import java.io.File;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    /**
     * 线程池
     * */
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(5, 5, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

    /**
     * 缓存目录
     * */
    public static final String CACHE_PATH = CONFIG_PATH + "/cache";

}