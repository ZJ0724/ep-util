package com.easipass.util.core;

import com.easipass.util.core.DTO.CusResultDTO;
import com.easipass.util.core.sftp.CusResultUploadSftp;
import com.easipass.util.core.exception.ErrorException;

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