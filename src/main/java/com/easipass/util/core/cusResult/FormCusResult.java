package com.easipass.util.core.cusResult;

import com.easipass.util.core.DTO.CusResultDTO;
import com.easipass.util.core.Database;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.CusResult;
import com.easipass.util.core.exception.CusResultException;
import com.easipass.util.core.exception.ErrorException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 报关单回执
 *
 * @author ZJ
 * */
public abstract class FormCusResult extends CusResult {

    /**
     * ediNo
     * */
    private final String ediNo;

    /**
     * seqNo
     * */
    private String seqNo;

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
    public final String getSeqNo() {
        if (this.seqNo != null) {
            return this.seqNo;
        }

        SWGDDatabase swgdOracle = new SWGDDatabase();
        ResultSet resultSet = swgdOracle.queryFormHead(this.ediNo);
        String seqNo;

        try {
            if (!resultSet.next()) {
                throw new CusResultException("数据库不存在报关单: " + this.ediNo);
            }
            seqNo = Database.getFiledData(resultSet, "SEQ_NO");
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdOracle.close();
        }

        if (seqNo == null || "".equals(seqNo)) {
            seqNo = "seqNo00000000" + this.ediNo.substring(this.ediNo.length() - 5);
        }

        this.seqNo = seqNo;

        return seqNo;
    }

    /**
     * 获取ediNo
     *
     * @return ediNo
     * */
    public final String getEdiNo() {
        return this.ediNo;
    }

}