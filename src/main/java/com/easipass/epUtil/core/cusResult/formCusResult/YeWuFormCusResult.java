package com.easipass.epUtil.core.cusResult.formCusResult;

import com.easipass.epUtil.core.cusResult.FormCusResult;
import com.easipass.epUtil.core.dto.CusResultDTO;

/**
 * 报关单业务回执
 *
 * @author ZJ
 * */
public class YeWuFormCusResult extends FormCusResult {

    /**
     * 构造函数
     *
     * @param cusResultDTO cusResultDTO
     * @param ediNo ediNo
     */
    public YeWuFormCusResult(CusResultDTO cusResultDTO, String ediNo) {
        super(cusResultDTO, ediNo);
    }

    @Override
    protected String getData() {
        return null;
    }

    @Override
    protected String getFileName() {
        return null;
    }

}