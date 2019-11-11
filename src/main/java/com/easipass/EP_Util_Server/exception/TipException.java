package com.easipass.EP_Util_Server.exception;

public class TipException extends RuntimeException {

    private String message;

    public TipException(){

    }
    public TipException(String message){
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
