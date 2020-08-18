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
         * type
         *
         * 0：ediNo
         * 1：preEntryId
         * 2：seqNo
         * */
        private String type;

        /**
         * 数据
         * */
        private String data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
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