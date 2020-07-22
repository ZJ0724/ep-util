package com.easipass.epUtil.entity.resources.cusResult.decModCusResult;

import com.easipass.epUtil.entity.Resource;

/**
 * 修撤单QP回执资源
 *
 * @author ZJ
 * */
public final class QPDecModCusResultResource extends Resource {

    /**
     * 单例
     * */
    private static final QPDecModCusResultResource QP_DEC_MOD_CUS_RESULT_RESOURCE = new QPDecModCusResultResource();

    /**
     * 构造函数
     */
    private QPDecModCusResultResource() {
        super(DEC_MOD_CUS_RESULT_PATH + "QPDecModCusResult");
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static QPDecModCusResultResource getInstance() {
        return QP_DEC_MOD_CUS_RESULT_RESOURCE;
    }

}