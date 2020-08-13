package com.easipass.util.core.config;

import com.easipass.util.core.Project;
import com.easipass.util.util.FileUtil;
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
    private int port = 8001;

    /**
     * 文件
     * */
    private static final File FILE = new File(Project.CONFIG_PATH, "port");

    /**
     * 单例
     * */
    private static Port PORT = null;

    /**
     * 构造函数
     * */
    private Port() {
        if (FILE.exists()) {
            this.port = Integer.parseInt(FileUtil.getData(FILE));
        } else {
            FileUtil.createFile(FILE);
            FileUtil.setData(FILE, this.port + "");
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
        if (PORT == null) {
            PORT = new Port();
        }

        return PORT;
    }

}