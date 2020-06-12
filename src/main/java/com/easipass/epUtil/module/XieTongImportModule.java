package com.easipass.epUtil.module;

import com.easipass.epUtil.component.Http;

/**
 * 协同导入模块
 *
 * @author ZJ
 * */
public class XieTongImportModule {

    /** 单例 */
    private final static XieTongImportModule XIE_TONG_IMPORT_MODULE = new XieTongImportModule();

    /**
     * 构造函数
     * */
    private XieTongImportModule() {}

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static XieTongImportModule getXieTongImportModule() {
        return XIE_TONG_IMPORT_MODULE;
    }

    /**
     * 导入
     * */
    public void xieTongImport(String data) {
        // 第三方加签接口
//        Http http = new Http("192.168.120.83:9091/swgdsupplyspboot/test/sign", "POST", );
    }

}