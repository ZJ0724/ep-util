package com.easipass.epUtil.exception;

/**
 * 报文异常
 *
 * @author ZJ
 * */
public final class CusFileException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param message 信息
     * */
    private CusFileException(String message) {
        super(message);
    }

    /**
     * 不是正确的报文文件
     *
     * @return 异常实体
     * */
    public static CusFileException notCusFile() {
        return new CusFileException("不是正确的报文文件，请重新选择！");
    }

}