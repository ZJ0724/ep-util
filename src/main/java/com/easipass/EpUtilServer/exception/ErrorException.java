package com.easipass.EpUtilServer.exception;

public class ErrorException extends RuntimeException {

    private String message;

    public ErrorException(){

    }
    public ErrorException(String message){
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
