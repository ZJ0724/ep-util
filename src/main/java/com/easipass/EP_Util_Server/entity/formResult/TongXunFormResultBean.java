package com.easipass.EP_Util_Server.entity.formResult;

import org.springframework.stereotype.Controller;

@Controller
public class TongXunFormResultBean {

    private String responseCode;
    private String errorMessage;
    private String clientSeqNo;
    private String seqNo;
    private String trnPreId;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getClientSeqNo() {
        return clientSeqNo;
    }

    public void setClientSeqNo(String clientSeqNo) {
        this.clientSeqNo = clientSeqNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getTrnPreId() {
        return trnPreId;
    }

    public void setTrnPreId(String trnPreId) {
        this.trnPreId = trnPreId;
    }

}