package com.easipass.EP_Util_Server.entity.decModResult;

public class YeWuDecModResult {

    private String fileName;
    private YeWuDecModResultData yeWuDecModResultData;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public YeWuDecModResultData getYeWuDecModResultData() {
        return yeWuDecModResultData;
    }

    public void setYeWuDecModResultData(YeWuDecModResultData yeWuDecModResultData) {
        this.yeWuDecModResultData = yeWuDecModResultData;
    }

    /**
     * 检查
     * */
    public boolean check(){
        if(fileName==null||!yeWuDecModResultData.check()){
            return false;
        }
        return true;
    }

}