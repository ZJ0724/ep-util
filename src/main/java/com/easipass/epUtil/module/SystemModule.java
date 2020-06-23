package com.easipass.epUtil.module;

import com.easipass.epUtil.entity.Version;

/**
 * 系统模块
 *
 * @author ZJ
 * */
public class SystemModule {

    /** version */
    private final Version version = Version.getVersionInstance();

    /** 单例 */
    private final static SystemModule VERSION_MODULE = new SystemModule();

    /**
     * 构造函数
     * */
    private SystemModule() {}

    /**
     * 获取版本信息
     *
     * @return 版本信息
     * */
    public String getVersion() {
        return version.getVersion();
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static SystemModule getVersionModuleInstance() {
        return VERSION_MODULE;
    }

}