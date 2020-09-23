package com.easipass.util.core.sftp;

import com.easipass.util.core.CusResult;
import com.easipass.util.core.Sftp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 回执上传sftp
 *
 * @author ZJ
 * */
public final class CusResultUploadSftp extends Sftp {

    /**
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(CusResultUploadSftp.class);

    /**
     * 构造函数
     */
    public CusResultUploadSftp() {
        super("192.168.120.83", 22, "gccoper", "gccoper");
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

        this.upload("/gcchome/winx/cus/cfg_c2e", cusResult.getName(), data);
    }

}