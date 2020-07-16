package com.easipass.epUtil.core.entity.resources.cusResult.formCusResult;

import com.easipass.epUtil.core.entity.Resource;

/**
 * 报关单通讯回执资源
 *
 * @author ZJ
 * */
public final class TongXunFormCusResultResource extends Resource {

    /**
     * 单例
     * */
    private static final TongXunFormCusResultResource TONG_XUN_FORM_CUS_RESULT_RESOURCE = new TongXunFormCusResultResource();

    /**
     * 构造函数
     */
    private TongXunFormCusResultResource() {
        super(FORM_CUS_RESULT_PATH + "tongXunFormCusResult");
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static TongXunFormCusResultResource getInstance() {
        return TONG_XUN_FORM_CUS_RESULT_RESOURCE;
    }

}