package com.easipass.epUtil.entity;

import com.easipass.epUtil.entity.DTO.CusResultDTO;
import com.easipass.epUtil.entity.sftp.Sftp83;
import com.easipass.epUtil.exception.BaseException;
import com.easipass.epUtil.exception.ErrorException;

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
     *
     * @return 响应
     * */
    public final Response upload() {
        Sftp83 sftp = null;

        try {
            sftp = new Sftp83();
            sftp.connect();
            sftp.uploadCusResult(this);
        } catch (BaseException e) {
            return Response.returnFalse(e.getMessage());
        } finally {
            if (sftp != null) {
                sftp.close();
            }
        }

        ChromeDriver chromeDriver = null;

        try {
            chromeDriver = ChromeDriver.getChromeDriver();
            chromeDriver.swgdRecvRun();
        } catch (BaseException e) {
            return Response.returnFalse(e.getMessage());
        } finally {
            if (chromeDriver != null) {
                chromeDriver.close();
            }
        }

        return Response.returnTrue();
    }

    /**
     * 一次性上传回执
     *
     * @param cusResult1 回执1
     * @param cusResult2 回执2
     *
     * @return 响应
     * */
    public static Response disposableUpload(CusResult cusResult1, CusResult cusResult2) {
        // sftp83
        Sftp83 sftp83 = null;
        // 谷歌驱动
        ChromeDriver chromeDriver = null;

        try {
            sftp83 = new Sftp83();
            sftp83.connect();

            chromeDriver = ChromeDriver.getChromeDriver();

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
            return Response.returnFalse(e.getMessage());
        } finally {
            if (sftp83 != null) {
                sftp83.close();
            }
            if (chromeDriver != null) {
                chromeDriver.close();
            }
        }

        return Response.returnTrue();
    }

}