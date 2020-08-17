package com.easipass.util.core.DTO.cusResult;

import com.zj0724.util.springboot.parameterCheck.NotNull;

/**
 * 通讯回执DTO
 *
 * @author ZJ
 * */
public final class TongXunFormCusResultDTO {

    @NotNull("relation节点缺失")
    private Relation relation;

    @NotNull("cusResult节点缺失")
    private CusResult cusResult;

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public CusResult getCusResult() {
        return cusResult;
    }

    public void setCusResult(CusResult cusResult) {
        this.cusResult = cusResult;
    }

    /**
     * Relation
     * */
    public static final class Relation {

        /**
         * ediNo
         * */
        private String ediNo;

        public String getEdiNo() {
            return ediNo;
        }

        public void setEdiNo(String ediNo) {
            this.ediNo = ediNo;
        }

        @Override
        public String toString() {
            return "Relation{" +
                    "ediNo='" + ediNo + '\'' +
                    '}';
        }

    }

    /**
     * CusResult
     * */
    public static final class CusResult extends CusResultDTO {

        /**
         * seqNo
         * */
        private String seqNo;

        public String getSeqNo() {
            return seqNo;
        }

        public void setSeqNo(String seqNo) {
            this.seqNo = seqNo;
        }

        @Override
        public String toString() {
            return "CusResult{" +
                    "seqNo='" + seqNo + '\'' +
                    '}';
        }

    }

}