package com.easipass.util.entity.resources.cusResult.formCusResult;

import com.easipass.util.entity.Resource;

/**
 * 报关单业务回执资源
 *
 * @author ZJ
 * */
public final class YeWuFormCusResultResource extends Resource {

    /**
     * 单例
     * */
    private static final YeWuFormCusResultResource YE_WU_FORM_CUS_RESULT_RESOURCE = new YeWuFormCusResultResource();

    /**
     * 构造函数
     */
    private YeWuFormCusResultResource() {
        super(FORM_CUS_RESULT_PATH + "yeWuFormCusResult");
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static YeWuFormCusResultResource getInstance() {
        return YE_WU_FORM_CUS_RESULT_RESOURCE;
    }

}