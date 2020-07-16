package com.easipass.epUtil.core.entity.resources.config;

import com.easipass.epUtil.core.entity.Resource;

/**
 * sftp83配置文件资源
 *
 * @author ZJ
 */
public class Sftp83PropertiesResource extends Resource {


    /**
     * 单例
     * */
    private static final Sftp83PropertiesResource SFTP_83_PROPERTIES_RESOURCE = new Sftp83PropertiesResource();

    /**
     * 构造函数
     */
    private Sftp83PropertiesResource() {
        super(CONFIG_PATH + "sftp83.properties");
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static Sftp83PropertiesResource getInstance() {
        return SFTP_83_PROPERTIES_RESOURCE;
    }

}