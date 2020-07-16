package com.easipass.epUtil.core.entity.resources.config;

import com.easipass.epUtil.core.entity.Resource;

/**
 * SWGD数据库配置资源
 *
 * @author ZJ
 * */
public class SWGDPropertiesResource extends Resource {

    /**
     * 单例
     * */
    private static final SWGDPropertiesResource SWGD_PROPERTIES_RESOURCE = new SWGDPropertiesResource();

    /**
     * 构造函数
     */
    private SWGDPropertiesResource() {
        super(CONFIG_PATH + "SWGD.properties");
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static SWGDPropertiesResource getInstance() {
        return SWGD_PROPERTIES_RESOURCE;
    }

}