package com.easipass.util.core.config;

import com.easipass.util.core.SystemType;
import com.easipass.util.core.entity.Task;
import com.easipass.util.core.util.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(200, 200, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

    /**
     * 缓存目录
     * */
    public static final String CACHE_PATH = CONFIG_PATH + "/cache";

    /**
     * 配置文件目录
     * */
    public static final String CONFIG_FILE_PATH = CONFIG_PATH + "/config";

    /**
     * 任务集合
     * */
    public static final List<Task> TASKS = new ArrayList<>();

    /**
     * 缓存文件
     * */
    public static final List<String> CACHE_FILE = new ArrayList<>();

}