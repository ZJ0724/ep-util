package com.easipass.epUtil.entity.result;

import com.easipass.epUtil.entity.Result;
import com.easipass.epUtil.entity.dto.ResultDTO;
import com.easipass.epUtil.module.oracle.SWGDOracle;

public abstract class FormResult extends Result {

    /**
     * ediNo
     * */
    private String ediNo;

    /**
     * 构造函数
     */
    protected FormResult(String ediNo, ResultDTO resultDTO) {
        super(resultDTO);
        setEdiNo(ediNo);
        init();
    }

    /**
     * get,set
     * */
    public String getEdiNo() {
        return ediNo;
    }

    public void setEdiNo(String ediNo) {
        this.ediNo = ediNo;
    }

    /**
     * 获取seqNo
     * */
    protected String getSeqNo() {
        String ediNo = this.getEdiNo();

        return "seqNo00000000" + ediNo.substring(ediNo.length() - 5);
    }

    /**
     * 获取报关单号
     * */
    protected String getPreEntryId() {
        SWGDOracle swgdOracle = new SWGDOracle();
        String ediNo = this.getEdiNo();

        return swgdOracle.queryDeclPort(ediNo) + "000000000" + ediNo.substring(ediNo.length() - 5);
    }

}