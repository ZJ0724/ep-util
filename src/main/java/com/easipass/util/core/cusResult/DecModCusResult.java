package com.easipass.util.core.cusResult;

import com.easipass.util.core.CusResult;
import com.easipass.util.core.DTO.cusResult.CusResultDTO;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.exception.CusResultException;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.util.StringUtil;

import java.sql.SQLException;

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

        if (StringUtil.isEmpty(preEntryId)) {
            throw new CusResultException("preEntryId不能为空");
        }

        // 查询修撤单是否存在
        SWGDDatabase swgdDatabase = new SWGDDatabase();

        try {
            if (!swgdDatabase.queryDecModHead(this.preEntryId).next()) {
                throw new CusResultException("修撤单不存在");
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdDatabase.close();
        }
    }

    /**
     * 获取decModSeqNo
     *
     * @return decModSeqNo
     * */
    protected final String getDecModSeqNo() {
        SWGDDatabase swgdDatabase = new SWGDDatabase();
        String decModSeqNo;

        try {
            decModSeqNo = SWGDDatabase.getFiledData(swgdDatabase.queryDecModHead(this.preEntryId), "DECMODSEQNO", true);
        } finally {
            swgdDatabase.close();
        }

        if (decModSeqNo == null) {
            decModSeqNo = "decModSeqNo00" + this.preEntryId.substring(this.preEntryId.length() - 5);
        }

        return decModSeqNo;
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