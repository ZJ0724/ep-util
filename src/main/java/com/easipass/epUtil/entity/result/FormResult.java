package com.easipass.epUtil.entity.result;

import com.easipass.epUtil.entity.Result;
import com.easipass.epUtil.entity.dto.ResultDTO;

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



    @Override
    public String getFileName() {

    }

}