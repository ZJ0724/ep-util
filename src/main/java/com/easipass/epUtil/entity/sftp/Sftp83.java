package com.easipass.epUtil.entity.sftp;

import com.easipass.epUtil.core.CusResult;
import com.easipass.epUtil.entity.Sftp;
import com.easipass.epUtil.entity.config.Sftp83Properties;

/**
 * sftp83
 *
 * @author ZJ
 * */
public final class Sftp83 extends Sftp {

    /**
     * sftp83配置
     * */
    private final static Sftp83Properties SFTP_83_PROPERTIES = Sftp83Properties.getInstance();

    /**
     * 构造函数
     */
    public Sftp83() {
        super(SFTP_83_PROPERTIES.getUrl(), SFTP_83_PROPERTIES.getPort(), SFTP_83_PROPERTIES.getUsername(), SFTP_83_PROPERTIES.getPassword());
    }

    /**
     * 上传回执
     *
     * @param cusResult 回执
     * */
    public void uploadCusResult(CusResult cusResult) {
        this.upload(SFTP_83_PROPERTIES.getUploadPath(), cusResult.getName(), cusResult.getData());
    }

}