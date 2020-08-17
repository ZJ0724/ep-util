package com.easipass.util.core.DTO.cusResult;

import com.zj0724.util.springboot.parameterCheck.NotNull;

/**
 * 业务回执DTO
 *
 * @author ZJ
 * */
public final class YeWuFormCusResultDTO {

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

        /**
         * preEntryId
         * */
        private String preEntryId;

        /**
         * seqNo
         * */
        private String seqNo;

        public String getEdiNo() {
            return ediNo;
        }

        public void setEdiNo(String ediNo) {
            this.ediNo = ediNo;
        }

        public String getPreEntryId() {
            return preEntryId;
        }

        public void setPreEntryId(String preEntryId) {
            this.preEntryId = preEntryId;
        }

        public String getSeqNo() {
            return seqNo;
        }

        public void setSeqNo(String seqNo) {
            this.seqNo = seqNo;
        }
    }

    /**
     * cusResult
     * */
    public static final class CusResult extends CusResultDTO {
        /**
         * preEntryId
         * */
        private String preEntryId;

        public String getPreEntryId() {
            return preEntryId;
        }

        public void setPreEntryId(String preEntryId) {
            this.preEntryId = preEntryId;
        }
    }

}