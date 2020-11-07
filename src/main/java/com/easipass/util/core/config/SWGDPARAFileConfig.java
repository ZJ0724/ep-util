package com.easipass.util.core.config;

import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.util.FileUtil;
import java.io.File;
import java.io.IOException;

/**
 * SWGDPARA数据库配置文件
 *
 * @author ZJ
 * */
public final class SWGDPARAFileConfig {

    /**
     * 文件路径
     * */
    private static final String PATH = Project.CONFIG_FILE_PATH + "/SWGDPARA";

    /**
     * 生产
     * */
    private static final File PROD_FILE = new File(PATH, "PROD");

    /**
     * 开发
     * */
    private static final File DEV_FILE = new File(PATH, "DEV");

    /**
     * 本地
     * */
    private static final File LOCAL_FILE = new File(PATH, "LOCAL");

    /**
     * 缺省driverClass
     * */
    private static final String driverClass = "oracle.jdbc.OracleDriver";

    /**
     * 缺省jdbcUrl
     * */
    private static final String jdbcUrl = "jdbc:oracle:thin:@192.168.130.216:1521:testeport";

    /**
     * 缺省用户名
     * */
    private static final String user = "devtester";

    /**
     * 缺省密码
     * */
    private static final String password = "easytester";

    /**
     * 目前使用的文件
     * */
    public static final File currentFile;

    static {
        init(PROD_FILE);
        init(DEV_FILE);
        init(LOCAL_FILE);
        if (ENVConfig.currentENV == ENVConfig.PROD) {
            currentFile = PROD_FILE;
        } else if (ENVConfig.currentENV == ENVConfig.DEV) {
            currentFile = DEV_FILE;
        }else if (ENVConfig.currentENV == ENVConfig.LOCAL) {
            currentFile = LOCAL_FILE;
        } else {
            throw new ErrorException("环境异常");
        }
    }

    /**
     * 配置文件初始化
     *
     * @param file 文件
     * */
    private static void init(File file) {
        File filePAth = new File(PATH);
        if (!filePAth.exists()) {
            boolean mkdirs = filePAth.mkdirs();
            if (!mkdirs) {
                throw new ErrorException("创建SWGDPARA文件夹失败");
            }
        }
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    throw new ErrorException("创建" + file.getAbsolutePath() + "文件失败");
                }
                FileUtil.setData(file, "driverClass=" + driverClass + "\n");
                FileUtil.setData(file, "jdbcUrl=" + jdbcUrl + "\n", true);
                FileUtil.setData(file, "user=" + user + "\n", true);
                FileUtil.setData(file, "password=" + password, true);
            } catch (IOException e) {
                throw new ErrorException(e.getMessage());
            }
        }
    }

}