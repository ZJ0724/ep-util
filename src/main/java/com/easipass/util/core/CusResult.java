package com.easipass.util.core;

import com.easipass.util.core.DTO.cusResult.CusResultDTO;
import com.easipass.util.core.exception.CusResultException;
import com.easipass.util.core.sftp.CusResultUploadSftp;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.util.StringUtil;

/**
 * 回执
 *
 * @author ZJ
 */
public abstract class CusResult {

    /**
     * 状态
     * */
    private final String channel;

    /**
     * 备注
     * */
    private final String note;

    /**
     * 构造函数
     *
     * @param cusResultDTO cusResultDTO
     * */
    protected CusResult(CusResultDTO cusResultDTO) {
        this.channel = cusResultDTO.getChannel();
        this.note = cusResultDTO.getNote();

        if (StringUtil.isEmpty(this.channel)) {
            throw new CusResultException("channel不能为空");
        }

        if (StringUtil.isEmpty(this.note)) {
            throw new CusResultException("note不能为空");
        }
    }

    /**
     * 获取报文全内容
     *
     * @return 回执全内容
     * */
    public abstract String getData();

    /**
     * 获取回执文件名
     *
     * @return 文件名
     * */
    public abstract String getName();

    /**
     * 获取channel
     *
     * @return channel
     * */
    protected final String getChannel() {
        return this.channel;
    }

    /**
     * 获取note
     *
     * @return note
     * */
    protected final String getNote() {
        return this.note;
    }

    /**
     * 上传
     * */
    public final void upload() {
        CusResultUploadSftp sftp = null;

        try {
            sftp = new CusResultUploadSftp();
            sftp.uploadCusResult(this);
        } finally {
            if (sftp != null) {
                sftp.close();
            }
        }

        ChromeDriver chromeDriver = null;

        try {
            chromeDriver = new ChromeDriver();
            chromeDriver.swgdRecvRun();
        } finally {
            if (chromeDriver != null) {
                chromeDriver.close();
            }
        }
    }

    /**
     * 进出口标识
     *
     * @param ieFlag ieFlag
     *
     * @return 进口是1 出口是0
     * */
    public final String getIeFlag(String ieFlag) {
        switch (ieFlag) {
            case "0" : case "2" : case "4" : case "6" : case "8" : case "A" :
                return "O";

            case "1" : case "3" : case "5" : case "7" : case "9" : case "B" :
            case "D" :  case "F" :
                return "1";
        }

        return null;
    }

    /**
     * 一次性上传回执
     *
     * @param cusResult1 回执1
     * @param cusResult2 回执2
     * */
    public static void disposableUpload(CusResult cusResult1, CusResult cusResult2) {
        // sftp83
        CusResultUploadSftp sftp83 = null;
        // 谷歌驱动
        ChromeDriver chromeDriver = null;

        try {
            sftp83 = new CusResultUploadSftp();

            chromeDriver = new ChromeDriver();

            sftp83.uploadCusResult(cusResult1);
            chromeDriver.swgdRecvRun();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new ErrorException(e.getMessage());
            }

            sftp83.uploadCusResult(cusResult2);
            chromeDriver.swgdRecvRun();
        } catch (BaseException e) {
            throw (BaseException) e.getCause();
        } finally {
            if (sftp83 != null) {
                sftp83.close();
            }
            if (chromeDriver != null) {
                chromeDriver.close();
            }
        }
    }

}