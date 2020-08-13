package com.easipass.util.core.sftp;

import com.easipass.util.core.CusResult;
import com.easipass.util.core.Sftp;
import com.easipass.util.core.config.Sftp83Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(Sftp83.class);

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
        String data = cusResult.getData();

        log.info(data);

        this.upload(SFTP_83_PROPERTIES.getUploadPath(), cusResult.getName(), data);
    }

}