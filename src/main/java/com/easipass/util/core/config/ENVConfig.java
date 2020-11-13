package com.easipass.util.core.config;

import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.util.FileUtil;
import java.io.File;

/**
 * 环境
 *
 * @author ZJ
 * */
public enum ENVConfig {

    /**
     * 生产
     * */
    PROD("PROD"),

    /**
     * 开发
     * */
    DEV("DEV"),

    /**
     * 本地
     * */
    LOCAL("LOCAL");

    /**
     * 环境
     * */
    private final String ENVString;

    /**
     * 环境配置文件
     * */
    private static final File ENV_FILE = new File(Project.CONFIG_FILE_PATH, "ENV");

    /**
     * 当前环境
     * */
    public static ENVConfig currentENV;

    static {
        if (!ENV_FILE.exists()) {
            FileUtil.createFile(ENV_FILE);
            FileUtil.setData(ENV_FILE, ENVConfig.PROD.ENVString);
        }

        String data = FileUtil.getData(ENV_FILE);
        boolean b = false;
        for (ENVConfig envConfig : ENVConfig.values()) {
            if (envConfig.ENVString.equals(data)) {
                currentENV = envConfig;
                b = true;
                break;
            }
        }
        if (!b) {
            throw new ErrorException("环境异常");
        }
    }

    /**
     * 构造函数
     *
     * @param ENVString 环境
     * */
    ENVConfig(String ENVString) {
        this.ENVString = ENVString;
    }

}