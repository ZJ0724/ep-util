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
    private String url = "192.168.120.83";

    /**
     * port
     * */
    @Key
    private Integer port = 22;

    /**
     * username
     * */
    @Key
    private String username = "gccoper";

    /**
     * password
     * */
    @Key
    private String password = "gccoper";

    /**
     * 上传路径
     * */
    @Key
    private String uploadPath = "/gcchome/winx/cus/cfg_c2e";

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
     * getter
     * */
    public String getUrl() {
        return url;
    }

    public Integer getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static CusResultUploadSftpConfig getInstance() {
        return CUS_RESULT_UPLOAD_SFTP_CONFIG;
    }

}