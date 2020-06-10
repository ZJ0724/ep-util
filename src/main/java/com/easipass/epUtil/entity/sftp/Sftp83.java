package com.easipass.epUtil.entity.sftp;

import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.Result;
import com.easipass.epUtil.entity.Sftp;

public class Sftp83 extends Sftp {

    /**
     * sftp83配置
     * */
    private final com.easipass.epUtil.entity.config.Sftp83 sftp83 = Config.getConfig().getSftp83();

    /**
     * 构造函数
     */
    public Sftp83() {
        this.setUrl(sftp83.getUrl());
        this.setPort(sftp83.getPort());
        this.setUsername(sftp83.getUsername());
        this.setPassword(sftp83.getPassword());
    }

    /**
     * 上传回执
     * */
    public void uploadResult(Result result) {
        this.upload(sftp83.getUploadPath(), result.getFileName(), result.getData());
    }

}