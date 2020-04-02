package com.easipass.EP_Util_Server.entity.decModResult;

public class QPDecModResultData {

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

    /**
     * 检查
     */
    public boolean check(){
        if(decModSeqNo==null||resultMessage==null||resultCode==null){
            return false;
        }
        return true;
    }

}