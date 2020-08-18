package com.easipass.util.core.DTO.cusResult;

import com.zj0724.util.springboot.parameterCheck.NotNull;

/**
 * DecModCusResultDTO
 *
 * @author ZJ
 * */
public final class DecModCusResultDTO {

    /**
     * relation
     * */
    @NotNull("relation节点缺失")
    private Relation relation;

    /**
     * cusResult
     * */
    @NotNull("cusResult节点缺失")
    private CusResultDTO cusResult;

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public CusResultDTO getCusResult() {
        return cusResult;
    }

    public void setCusResult(CusResultDTO cusResult) {
        this.cusResult = cusResult;
    }

    /**
     * Relation
     * */
    public static final class Relation {
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