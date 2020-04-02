package com.easipass.EP_Util_Server.entity;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class AgentResult {

    private String responseCode;
    private String responseNotes;
    private String copCusCode;
    private String consignNo;
    private String decEntryID;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseNotes() {
        return responseNotes;
    }

    public void setResponseNotes(String responseNotes) {
        this.responseNotes = responseNotes;
    }

    public String getCopCusCode() {
        return copCusCode;
    }

    public void setCopCusCode(String copCusCode) {
        this.copCusCode = copCusCode;
    }

    public String getConsignNo() {
        return consignNo;
    }

    public void setConsignNo(String consignNo) {
        this.consignNo = consignNo;
    }

    public String getDecEntryID() {
        return decEntryID;
    }

    public void setDecEntryID(String decEntryID) {
        this.decEntryID = decEntryID;
    }

    @Override
    public String toString() {
        return "AgentResult{" +
                "responseCode='" + responseCode + '\'' +
                ", responseNotes='" + responseNotes + '\'' +
                ", copCusCode='" + copCusCode + '\'' +
                ", consignNo='" + consignNo + '\'' +
                ", decEntryID='" + decEntryID + '\'' +
                '}';
    }

}
