package com.easipass.util.core.config;

import com.easipass.util.core.Config;
import com.easipass.util.core.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 打卡配置
 *
 * @author ZJ
 * */
public final class DaKaConfig extends Config {

    /**
     * 打卡日期
     * */
    @Key
    public List<String> date;

    /**
     * 打卡星期
     * */
    @Key
    public List<String> week;

    /**
     * 上班打卡时间
     * */
    @Key
    public String toWorkTime;

    /**
     * 下班打卡时间
     * */
    @Key
    public String offWorkTime;

    /**
     * 打卡账号
     * */
    @Key
    public String username;

    /**
     * 打卡密码
     * */
    @Key
    public String password;

    /**
     * 打卡标识
     *
     * 1: 开启
     * 0: 关闭
     * */
    @Key
    public String sign;

    /**
     * 单例
     * */
    private static final DaKaConfig DA_KA_CONFIG = new DaKaConfig();

    /**
     * 构造函数
     */
    private DaKaConfig() {
        super(Resource.DA_KA);
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static DaKaConfig getInstance() {
        return DA_KA_CONFIG;
    }

    @Override
    protected void setDefaultData() {
        date = new ArrayList<>();
        week = Arrays.asList("星期一", "星期二", "星期三", "星期四", "星期五");
        toWorkTime = "09:00";
        offWorkTime = "17:31";
        username = "jzhou";
        password = "easipass";
        sign = "1";
    }

}