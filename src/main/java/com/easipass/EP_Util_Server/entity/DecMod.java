package com.easipass.EP_Util_Server.entity;

public class DecMod {

    private String preEntryId;
    private String fileName;

    public String getPreEntryId() {
        return preEntryId;
    }

    public void setPreEntryId(String preEntryId) {
        this.preEntryId = preEntryId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 检查
     * */
    public boolean check(){
        if(fileName==null||preEntryId==null){
            return false;
        }
        return true;
    }

}