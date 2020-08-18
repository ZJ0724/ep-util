package com.easipass.util.core.DTO.cusResult;

import com.zj0724.util.springboot.parameterCheck.NotNull;

/**
 * AgentCusResultDTO
 *
 * @author ZJ
 * */
public final class AgentCusResultDTO {

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
         * ediNo
         * */
        private String ediNo;

        public String getEdiNo() {
            return ediNo;
        }

        public void setEdiNo(String ediNo) {
            this.ediNo = ediNo;
        }
    }

}