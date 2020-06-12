package com.easipass.epUtil.entity;

import com.easipass.epUtil.component.Log;
import com.easipass.epUtil.config.ProjectConfig;
import com.easipass.epUtil.config.ResourcePathConfig;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.util.FileUtil;
import java.io.IOException;
import java.io.InputStream;

/**
 * 打卡标记实体。
 * 对应配置文件：daKaSign
 * 采用单例模式
 *
 * @author ZJ
 * */
public class DaKaSign {

    /** 配置文件数据 */
    private String data;

    /** 单例 */
    private final static DaKaSign DA_KA_SIGN = new DaKaSign();

    /**  日志模块 */
    private final Log log = Log.getLog();

    /**
     * 构造函数
     * */
    private DaKaSign() {
        // 检查是否存在配置文件，不存在生成默认配置文件
        if (!ProjectConfig.DAKA_SIGN.exists()) {
            InputStream inputStream = DaKaSign.class.getResourceAsStream(ResourcePathConfig.DAKA_SIGN);
            FileUtil.copyTextFile(inputStream, ProjectConfig.DAKA_SIGN);
            try {
                inputStream.close();
            } catch (IOException e) {
                throw ErrorException.getErrorException(e.getMessage());
            }
        }

        // 加载数据
        this.loadData();
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static DaKaSign getDaKaSign() {
        return DA_KA_SIGN;
    }

    /**
     * 获取数据
     *
     * @return 数据
     * */
    public String getData() {
        return data;
    }

    /**
     * 设置数据
     *
     * @param data 要设置的数据
     * */
    public void setDaKa(String data) {
        FileUtil.setData(ProjectConfig.DAKA_SIGN, data);

        // 重新加载
        this.loadData();
    }

    /**
     * 加载数据
     * */
    public void loadData() {
        log.info("正在加载daKaSign...");
        data = FileUtil.getData(ProjectConfig.DAKA_SIGN);
        log.info(data);
    }

}