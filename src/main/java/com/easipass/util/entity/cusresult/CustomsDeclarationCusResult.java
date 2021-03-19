package com.easipass.util.entity.cusresult;

import com.easipass.util.entity.CusResult;

/**
 * 报关单回执
 *
 * @author ZJ
 * */
public final class CustomsDeclarationCusResult extends CusResult {

    /**
     * 报关单号
     * */
    private String customsDeclarationNumber;

    public String getCustomsDeclarationNumber() {
        return customsDeclarationNumber;
    }

    public void setCustomsDeclarationNumber(String customsDeclarationNumber) {
        this.customsDeclarationNumber = customsDeclarationNumber;
    }

}