package com.easipass.util.core.config;

import com.easipass.util.core.Resource;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.util.FileUtil;
import java.io.File;

/**
 * 端口配置文件
 *
 * @author ZJ
 * */
public final class Port {

    /**
     * 端口
     * */
    private final int port;

    /**
     * 文件
     * */
    private static final File FILE = new File(Resource.PORT.getPath());

    /**
     * 单例
     * */
    private static final Port PORT = new Port();

    /**
     * 构造函数
     * */
    private Port() {
        if (FILE.exists()) {
            try {
                String port = FileUtil.getData(FILE);
                if (port == null) {
                    throw new ErrorException("端口文件不存在");
                }
                this.port = Integer.parseInt(port);
            } catch (NumberFormatException e) {
                throw new ErrorException(e.getMessage());
            }
        } else {
            final int port = 8002;

            FileUtil.createFile(FILE);
            FileUtil.setData(FILE, port + "");
            this.port = port;
        }
    }

    /**
     * 获取端口
     *
     * @return 端口
     * */
    public int getPort() {
        return this.port;
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static Port getInstance() {
        return PORT;
    }

}