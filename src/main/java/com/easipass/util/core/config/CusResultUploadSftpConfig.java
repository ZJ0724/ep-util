package com.easipass.util.core.config;

import com.easipass.util.core.Config;
import com.easipass.util.core.Resource;

/**
 * 回执上传sftp配置
 *
 * @author ZJ
 * */
public final class CusResultUploadSftpConfig extends Config {

    /**
     * url
     * */
    @Key
    public String url;

    /**
     * port
     * */
    @Key
    public Integer port;

    /**
     * username
     * */
    @Key
    public String username;

    /**
     * password
     * */
    @Key
    public String password;

    /**
     * 上传路径
     * */
    @Key
    public String uploadPath;

    /**
     * 单例
     * */
    private static final CusResultUploadSftpConfig CUS_RESULT_UPLOAD_SFTP_CONFIG = new CusResultUploadSftpConfig();

    /**
     * 构造函数
     */
    private CusResultUploadSftpConfig() {
        super(Resource.CUS_RESULT_UPLOAD_SFTP);
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static CusResultUploadSftpConfig getInstance() {
        return CUS_RESULT_UPLOAD_SFTP_CONFIG;
    }

    @Override
    protected void setDefaultData() {
        url = "192.168.120.83";
        port = 22;
        username = "gccoper";
        password = "gccoper";
        uploadPath = "/gcchome/winx/cus/cfg_c2e";
    }

}