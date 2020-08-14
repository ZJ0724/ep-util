package com.easipass.util.core.sftp;

import com.easipass.util.core.CusResult;
import com.easipass.util.core.Sftp;
import com.easipass.util.core.config.CusResultUploadSftpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 回执上传sftp
 *
 * @author ZJ
 * */
public final class CusResultUploadSftp extends Sftp {

    /**
     * 配置
     * */
    private static final CusResultUploadSftpConfig CUS_RESULT_UPLOAD_SFTP_CONFIG = CusResultUploadSftpConfig.getInstance();

    /**
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(CusResultUploadSftp.class);

    /**
     * 构造函数
     */
    public CusResultUploadSftp() {
        super(CUS_RESULT_UPLOAD_SFTP_CONFIG.url, CUS_RESULT_UPLOAD_SFTP_CONFIG.port, CUS_RESULT_UPLOAD_SFTP_CONFIG.username, CUS_RESULT_UPLOAD_SFTP_CONFIG.password);
        this.connect();
    }

    /**
     * 上传回执
     *
     * @param cusResult 回执
     * */
    public void uploadCusResult(CusResult cusResult) {
        String data = cusResult.getData();

        log.info(data);

        this.upload(CUS_RESULT_UPLOAD_SFTP_CONFIG.uploadPath, cusResult.getName(), data);
    }

}