package com.easipass.EP_Util_Server.entity.decModResult;

public class YeWuDecModResultData {

    private String destResourceId;
    private String feedbackResults;
    private String resultNote;

    public String getDestResourceId() {
        return destResourceId;
    }

    public void setDestResourceId(String destResourceId) {
        this.destResourceId = destResourceId;
    }

    public String getFeedbackResults() {
        return feedbackResults;
    }

    public void setFeedbackResults(String feedbackResults) {
        this.feedbackResults = feedbackResults;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    /**
     * 校验
     * */
    public boolean check(){
        if(destResourceId==null||feedbackResults==null||resultNote==null){
            return false;
        }else{
            return true;
        }
    }

}