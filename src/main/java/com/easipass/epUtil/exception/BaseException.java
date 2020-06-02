package com.easipass.epUtil.exception;

public class BaseException extends RuntimeException {

    private String message;

    protected BaseException(String message) {
        super(message);
    }

}