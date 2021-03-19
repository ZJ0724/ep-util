package com.easipass.util.entity;

import com.easipass.util.config.BaseConfig;
import com.zj0724.common.exception.ErrorException;
import com.zj0724.common.util.FileUtil;
import com.zj0724.common.util.StringUtil;
import java.io.File;

/**
 * 端口
 *
 * @author ZJ
 * */
public final class Port {

    private static final Port PORT = new Port();

    private final int port;

    private Port() {
        File file = new File(BaseConfig.ROOT_PATH, "config/port");
        String data = FileUtil.getData(file);
        if (StringUtil.isEmpty(data)) {
            throw new ErrorException("端口配置出错");
        }
        try {
            this.port = Integer.parseInt(data);
        } catch (Exception e) {
            throw new ErrorException(e.getMessage());
        }
    }

    public static Port getInstance() {
        return PORT;
    }

    public int getPort() {
        return this.port;
    }

}