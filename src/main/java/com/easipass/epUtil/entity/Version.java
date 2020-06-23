package com.easipass.epUtil.entity;

import com.easipass.epUtil.config.ResourcePathConfig;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.util.FileUtil;
import java.io.IOException;
import java.io.InputStream;

/**
 * 版本
 *
 * @author ZJ
 * */
public class Version {

    /** 版本 */
    private final String version;

    /** 单例 */
    private final static Version VERSION = new Version();

    /**
     * 构造函数
     * */
    private Version() {
        InputStream inputStream = Version.class.getResourceAsStream(ResourcePathConfig.VERSION);
        this.version = FileUtil.getData(inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }
    }

    /**
     * 获取单例
     * */
    public static Version getVersionInstance() {
        return VERSION;
    }

    /**
     * 获取版本信息
     * */
    public String getVersion() {
        return this.version;
    }

}