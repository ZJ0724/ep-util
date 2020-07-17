package com.easipass.epUtil.entity.cusResult;

import com.easipass.epUtil.entity.oracle.SWGDOracle;
import com.easipass.epUtil.entity.CusResult;
import com.easipass.epUtil.entity.dto.CusResultDTO;

/**
 * 报关单回执
 *
 * @author ZJ
 * */
public abstract class FormCusResult extends CusResult {

    private final String ediNo;

    /**
     * 构造函数
     *
     * @param cusResultDTO cusResultDTO
     * @param ediNo ediNo
     */
    protected FormCusResult(CusResultDTO cusResultDTO, String ediNo) {
        super(cusResultDTO);
        this.ediNo = ediNo;
    }

    /**
     * 获取seqNo
     *
     * @return seqNo
     * */
    protected final String getSeqNo() {
        return "seqNo00000000" + this.ediNo.substring(this.ediNo.length() - 5);
    }

    /**
     * 获取报关单号
     *
     * @return 报关单号
     * */
    protected final String getPreEntryId() {
        return new SWGDOracle().queryDeclPort(this.ediNo) + "000000000" + this.ediNo.substring(this.ediNo.length() - 5);
    }

    /**
     * 获取ediNo
     *
     * @return ediNo
     * */
    protected final String getEdiNo() {
        return this.ediNo;
    }

}