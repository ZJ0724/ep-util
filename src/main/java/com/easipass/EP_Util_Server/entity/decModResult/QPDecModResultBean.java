package com.easipass.EP_Util_Server.entity.decModResult;

import org.springframework.stereotype.Controller;

public class QPDecModResultBean {

    private String decModSeqNo;
    private String resultMessage;
    private String resultCode;

    public String getDecModSeqNo() {
        return decModSeqNo;
    }

    public void setDecModSeqNo(String decModSeqNo) {
        this.decModSeqNo = decModSeqNo;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

}