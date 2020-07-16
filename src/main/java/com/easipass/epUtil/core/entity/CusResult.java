package com.easipass.epUtil.core.entity;

import com.easipass.epUtil.core.entity.dto.CusResultDTO;
import com.easipass.epUtil.core.entity.sftp.Sftp83;
import com.easipass.epUtil.core.exception.BaseException;
import com.easipass.epUtil.core.exception.UploadCusResultException;

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
            chromeDriver = new ChromeDriver();
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

}