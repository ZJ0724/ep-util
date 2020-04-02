package com.easipass.EP_Util_Server.exception;

public class UtilTipException extends Exception {

    private String message;

    public UtilTipException(){

    }
    public UtilTipException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}