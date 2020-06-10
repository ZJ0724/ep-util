package com.easipass.epUtil.entity;

import com.alibaba.fastjson.JSONObject;
import com.easipass.epUtil.config.ProjectConfig;
import com.easipass.epUtil.config.ResourcePathConfig;
import com.easipass.epUtil.entity.config.Sftp83;
import com.easipass.epUtil.entity.config.Swgd;
import com.easipass.epUtil.exception.ConfigException;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.util.FileUtil;
import java.io.*;

public class Config {

    /**
     * SWGD
     * */
    private final Swgd swgd = Swgd.getSWGD();

    /**
     * sftp83
     * */
    private final Sftp83 sftp83 = Sftp83.getSftp83();

    /**
     * 单例
     * */
    private final static Config config = new Config();

    /**
     * 构造函数
     * */
    private Config() {
        // 检查配置文件是否存在，不存在创建默认配置文件
        if (!ProjectConfig.CONFIG_FILE.exists()) {
            // 不存在创建默认配置文件
            InputStream inputStream = Config.class.getResourceAsStream(ResourcePathConfig.CONFIG_PATH);
            FileUtil.copyTextFile(inputStream, ProjectConfig.CONFIG_FILE);
            try {
                inputStream.close();
            } catch (IOException e) {
                throw ErrorException.getErrorException(e.getMessage());
            }
        }
    }

    /**
     * get
     * */
    public Swgd getSwgd() {
        return swgd;
    }

    public Sftp83 getSftp83() {
        return sftp83;
    }

    /**
     * 获取单例
     * */
    public static Config getConfig() {
        return config;
    }

    /**
     * 加载数据
     * */
    public void loadData() {
        try {
            // 配置文件数据
            String configData = FileUtil.getData(ProjectConfig.CONFIG_FILE);

            // json数据
            JSONObject jsonObject = JSONObject.parseObject(configData);

            // 加载SWGD
            this.swgd.loadData(jsonObject.getJSONObject("swgd"));

            // 加载sftp
            this.sftp83.loadData(jsonObject.getJSONObject("sftp83"));
        } catch (com.alibaba.fastjson.JSONException e) {
            throw ConfigException.configFileException();
        }
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

            this.loadData();
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getLocalizedMessage());
        }
    }

    @Override
    public String toString() {
        return "ConfigDTO{" +
                "swgd=" + swgd +
                ", sftp83=" + sftp83 +
                '}';
    }

}