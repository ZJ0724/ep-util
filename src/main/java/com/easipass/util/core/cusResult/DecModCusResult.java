package com.easipass.util.core.cusResult;

import com.easipass.util.core.CusResult;
import com.easipass.util.core.DTO.cusResult.CusResultDTO;

/**
 * 修撤单回执
 *
 * @author ZJ
 * */
public abstract class DecModCusResult extends CusResult {

    /**
     * 报关单号
     * */
    private final String preEntryId;

    /**
     * 构造函数
     *
     * @param cusResultDTO cusResultDTO
     * @param preEntryId 报关单号
     * */
    protected DecModCusResult(CusResultDTO cusResultDTO, String preEntryId) {
        super(cusResultDTO);
        this.preEntryId = preEntryId;
    }

    /**
     * 获取decModSeqNo
     *
     * @return decModSeqNo
     * */
    protected final String getDecModSeqNo() {
        return "decModSeqNo00" + this.preEntryId.substring(this.preEntryId.length() - 5);
    }

    /**
     * 获取报关单号
     *
     * @return 报关单号
     * */
    public final String getPreEntryId() {
        return this.preEntryId;
    }

}