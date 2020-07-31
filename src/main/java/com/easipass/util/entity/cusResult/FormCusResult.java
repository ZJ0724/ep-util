package com.easipass.util.entity.cusResult;

import com.easipass.util.entity.Oracle;
import com.easipass.util.entity.oracle.SWGDOracle;
import com.easipass.util.entity.CusResult;
import com.easipass.util.entity.DTO.CusResultDTO;
import com.easipass.util.exception.CusResultException;
import java.sql.ResultSet;

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
        SWGDOracle swgdOracle = new SWGDOracle();

        swgdOracle.connect();

        ResultSet resultSet = swgdOracle.queryFormHead(this.ediNo);

        if (resultSet == null) {
            swgdOracle.close();
            throw new CusResultException("数据库不存在ediNo: " + this.ediNo + "数据");
        }

        String seqNo = Oracle.getFiledData(resultSet, "SEQ_NO");

        swgdOracle.close();

        if (seqNo == null || "".equals(seqNo)) {
            return "seqNo00000000" + this.ediNo.substring(this.ediNo.length() - 5);
        } else {
            return seqNo;
        }
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