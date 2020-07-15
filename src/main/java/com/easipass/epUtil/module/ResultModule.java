package com.easipass.epUtil.module;

import com.easipass.epUtil.entity.ChromeDriver;
import com.easipass.epUtil.entity.Sftp;
import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.Result;
import com.easipass.epUtil.entity.config.Sftp83Properties;
import com.easipass.epUtil.exception.BaseException;
import com.easipass.epUtil.exception.DisposableUploadException;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.entity.sftp.Sftp83;

public class ResultModule {

    /** sftp83配置 */
    private final Sftp83Properties sftp83 = Config.getConfig().getSftp83();

    /** 单例 */
    private final static ResultModule RESULT_MODULE = new ResultModule();

    /**
     * 构造函数
     * */
    private ResultModule() {}

    /**
     * 获取单例
     *
     * @return ResultModule单例
     * */
    public static ResultModule getResultModule() {
        return RESULT_MODULE;
    }

    /**
     * 上传回执
     *
     * @param result 回执实体
     * */
    public void upload(Result result) {
        // 连接sftp
        Sftp sftp = new Sftp83();
        sftp.connect();
        sftp.upload(sftp83.getUploadPath(), result.getFileName(), result.getData());
        sftp.close();

        // 谷歌驱动点击RecvRun
        ChromeDriver chromeDriver = new ChromeDriver();
        chromeDriver.swgdRecvRun();
        chromeDriver.close();
    }

    /**
     * 一次性上传回执
     *
     * @param result1 回执实体1
     * @param result2 回执实体2
     * */
    public void DisposableUpload(Result result1, Result result2) {
        // sftp
        Sftp sftp = new Sftp83();
        // 谷歌驱动
        ChromeDriver chromeDriver = null;

        // 处理异常
        try {
            sftp.connect();
            chromeDriver = new ChromeDriver();

            // 上传第一个回执
            sftp.upload(sftp83.getUploadPath(), result1.getFileName(), result1.getData());
            chromeDriver.swgdRecvRun();

            // 等待500ms
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw ErrorException.getErrorException(e.getMessage());
            }

            // 再上传第二个回执
            sftp.upload(sftp83.getUploadPath(), result2.getFileName(), result2.getData());
            chromeDriver.swgdRecvRun();
        } catch (BaseException e) {
            throw DisposableUploadException.disposableUploadException(e.getMessage());
        } finally {
            // 关闭连接
            sftp.close();
            if (chromeDriver != null) {
                chromeDriver.close();
            }
        }
    }

}