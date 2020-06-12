package com.easipass.epUtil.module;

import com.easipass.epUtil.config.ProjectConfig;
import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.exception.ErrorException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ConfigModule {

    /** 单例 */
    private final static ConfigModule CONFIG_MODULE = new ConfigModule();

    /** config */
    private final Config config = Config.getConfig();

    /**
     * 构造函数
     * */
    private ConfigModule() {}

    /**
     * 获取单例
     *
     * @return ConfigModule单例
     * */
    public static ConfigModule getConfigModule() {
        return CONFIG_MODULE;
    }

    /**
     * 设置数据
     *
     * @param data 数据
     * */
    public void setData(String data) {
        try {
            OutputStream outputStream = new FileOutputStream(ProjectConfig.CONFIG_FILE);
            outputStream.write(data.getBytes());
            outputStream.close();

            config.loadData();
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getLocalizedMessage());
        }
    }

}