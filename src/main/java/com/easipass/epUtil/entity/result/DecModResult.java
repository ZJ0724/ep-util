package com.easipass.epUtil.entity.result;

import com.easipass.epUtil.entity.Result;
import com.easipass.epUtil.entity.dto.ResultDTO;

public abstract class DecModResult extends Result {

    /**
     * 报关单号
     * */
    private String preEntryId;

    /**
     * 构造函数
     * */
    protected DecModResult(String preEntryId, ResultDTO resultDTO) {
        super(resultDTO);
        setPreEntryId(preEntryId);
        init();
    }

    /**
     * get,set
     * */
    public String getPreEntryId() {
        return preEntryId;
    }

    public void setPreEntryId(String preEntryId) {
        this.preEntryId = preEntryId;
    }

    /**
     * 获取decModSeqNo
     * */
    protected String getDecModSeqNo() {
        return "decModSeqNo00" + this.preEntryId.substring(this.preEntryId.length() - 5);
    }

}