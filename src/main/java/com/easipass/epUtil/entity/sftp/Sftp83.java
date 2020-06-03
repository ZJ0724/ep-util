package com.easipass.epUtil.entity.sftp;

import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.Result;
import com.easipass.epUtil.entity.Sftp;

public class Sftp83 extends Sftp {

    /**
     * 构造函数
     */
    public Sftp83() {
        Config config = Config.getConfig();

        this.setUrl(config.getSftp83Url());
        this.setPort(config.getSftp83Port());
        this.setUsername(config.getSftp83Username());
        this.setPassword(config.getSftp83Password());
    }

    /**
     * 上传回执
     * */
    public void uploadResult(Result result) {
        Config config = Config.getConfig();

        this.upload(config.getSftp83UploadPath(), result.getFileName(), result.getData());
    }

}