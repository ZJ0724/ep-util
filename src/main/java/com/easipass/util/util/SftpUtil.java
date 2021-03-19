package com.easipass.util.util;

import com.easipass.util.Main;
import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.service.ConfigService;
import com.zj0724.common.component.Sftp;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.StringUtil;

public final class SftpUtil {

    public static Sftp getUploadCusResultSftp() {
        ConfigService configService = Main.APPLICATION_CONTEXT.getBean(ConfigService.class);
        ConfigPO ftpHost = configService.getByCode(ConfigPO.Code.UPLOAD_CUS_RESULT_SFTP_HOST);
        ConfigPO ftpPort = configService.getByCode(ConfigPO.Code.UPLOAD_CUS_RESULT_SFTP_PORT);
        ConfigPO ftpUsername = configService.getByCode(ConfigPO.Code.UPLOAD_CUS_RESULT_SFTP_USERNAME);
        ConfigPO ftpPassword = configService.getByCode(ConfigPO.Code.UPLOAD_CUS_RESULT_SFTP_PASSWORD);
        if (ftpHost == null || StringUtil.isEmpty(ftpHost.getData())) {
            throw new InfoException("上传回执ftp地址未配置");
        }
        if (ftpPort == null || StringUtil.isEmpty(ftpPort.getData())) {
            throw new InfoException("上传回执ftp端口未配置");
        }
        if (ftpUsername == null || StringUtil.isEmpty(ftpUsername.getData())) {
            throw new InfoException("上传回执ftp账号未配置");
        }
        if (ftpPassword == null || StringUtil.isEmpty(ftpPassword.getData())) {
            throw new InfoException("上传回执ftp密码未配置");
        }

        return new Sftp(ftpHost.getData(), Integer.parseInt(ftpPort.getData()), ftpUsername.getData(), ftpPassword.getData());
    }

}