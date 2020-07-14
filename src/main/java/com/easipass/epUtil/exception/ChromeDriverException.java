package com.easipass.epUtil.exception;

import com.easipass.epUtil.entity.Log;

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

    /**
     * 打卡失败
     *
     * @return 异常实体
     * */
    public static ChromeDriverException daKaException() {
        String errorMessage = "打卡失败";
        Log.getLog().error(errorMessage);
        return new ChromeDriverException(errorMessage);
    }

}