package com.easipass.EP_Util_Server.entity.decModResult;

public class QPDecModResult {

    private String fileName;
    private QPDecModResultData QPDecModResultData;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public com.easipass.EP_Util_Server.entity.decModResult.QPDecModResultData getQPDecModResultData() {
        return QPDecModResultData;
    }

    public void setQPDecModResultData(com.easipass.EP_Util_Server.entity.decModResult.QPDecModResultData QPDecModResultData) {
        this.QPDecModResultData = QPDecModResultData;
    }

    /**
     * 检查
     * */
    public boolean check(){
        if(fileName==null||!QPDecModResultData.check()){
            return false;
        }
        return true;
    }

}