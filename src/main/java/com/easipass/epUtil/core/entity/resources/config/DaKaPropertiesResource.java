package com.easipass.epUtil.core.entity.resources.config;

import com.easipass.epUtil.core.entity.Resource;

/**
 * 打卡配置资源文件
 *
 * @author ZJ
 * */
public final class DaKaPropertiesResource extends Resource {

    /**
     * 单例
     * */
    private static final DaKaPropertiesResource DA_KA_PROPERTIES_RESOURCE = new DaKaPropertiesResource();

    /**
     * 构造函数
     */
    private DaKaPropertiesResource() {
        super(CONFIG_PATH + "daKa.properties");
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static DaKaPropertiesResource getInstance() {
        return DA_KA_PROPERTIES_RESOURCE;
    }

}