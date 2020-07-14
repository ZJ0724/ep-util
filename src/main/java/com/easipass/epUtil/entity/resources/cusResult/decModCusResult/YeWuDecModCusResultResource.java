package com.easipass.epUtil.entity.resources.cusResult.decModCusResult;

import com.easipass.epUtil.entity.Resource;

/**
 * 修撤单业务回执资源
 *
 * @author ZJ
 * */
public class YeWuDecModCusResultResource extends Resource {

    /**
     * 单例
     * */
    private static final YeWuDecModCusResultResource YE_WU_DEC_MOD_CUS_RESULT_RESOURCE = new YeWuDecModCusResultResource();

    /**
     * 构造函数
     */
    private YeWuDecModCusResultResource() {
        super(DEC_MOD_CUS_RESULT_PATH + "yeWuDecModCusResult");
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static YeWuDecModCusResultResource getInstance() {
        return YE_WU_DEC_MOD_CUS_RESULT_RESOURCE;
    }

}