package com.easipass.util.core;

/**
 * 资源
 *
 * @author ZJ
 * */
public enum Resource {

    /**
     * 谷歌驱动-linux
     * */
    CHROME_DRIVER_LINUX(Project.ROOT_PATH + "/resources/chromeDriver/linux"),

    /**
     * 谷歌驱动-windows
     * */
    CHROME_DRIVER_WINDOWS(Project.ROOT_PATH + "/resources/chromeDriver/windows.exe"),

    /**
     * 报关单通讯回执
     * */
    TONG_XUN_FORM_CUS_RESULT(Project.ROOT_PATH + "/resources/cusResult/formCusResult/tongXunFormCusResult"),

    /**
     * 报关单业务回执
     * */
    YE_WU_FORM_CUS_RESULT(Project.ROOT_PATH + "/resources/cusResult/formCusResult/yeWuFormCusResult"),

    /**
     * 修撤单QP回执
     * */
    QP_DEC_MOD_CUS_RESULT(Project.ROOT_PATH + "/resources/cusResult/decModCusResult/QPDecModCusResult"),

    /**
     * 修撤单业务回执
     * */
    YE_WU_DEC_MOD_CUS_RESULT(Project.ROOT_PATH + "/resources/cusResult/decModCusResult/yeWuDecModCusResult"),

    /**
     * 代理委托回执
     * */
    AGENT_CUS_RESULT(Project.ROOT_PATH + "/resources/cusResult/agentCusResult.wts");

    /**
     * 路径
     * */
    private final String path;

    /**
     * 构造函数
     *
     * @param path 路径
     * */
    Resource(String path) {
        this.path = path;
    }

    /**
     * 获取路径
     *
     * @return 路径
     * */
    public final String getPath() {
        return this.path;
    }

}