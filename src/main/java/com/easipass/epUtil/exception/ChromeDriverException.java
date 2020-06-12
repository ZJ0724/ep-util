package com.easipass.epUtil.exception;

public class ChromeDriverException extends BaseException {

    private ChromeDriverException(String message) {
        super(message);
    }

    /**
     * 驱动文件异常
     * */
    public static ChromeDriverException chromeDriverFileException() {
        return new ChromeDriverException("驱动文件异常，请更换驱动文件");
    }

}