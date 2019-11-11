package com.easipass.EP_Util_Server.exception;

public class BugException extends RuntimeException {

    private String message;

    public BugException(){

    }
    public BugException(String message){
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
