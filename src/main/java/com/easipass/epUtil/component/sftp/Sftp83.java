package com.easipass.epUtil.component.sftp;

import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.component.Sftp;

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

}