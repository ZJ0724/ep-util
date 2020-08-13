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
    private List<String> date = new ArrayList<>();
    /**
     * 打卡星期
     * */
    @Key
    private List<String> week = Arrays.asList("星期一", "星期二", "星期三", "星期四", "星期五");

    /**
     * 上班打卡时间
     * */
    @Key
    private String toWorkTime = "09:00";

    /**
     * 下班打卡时间
     * */
    @Key
    private String offWorkTime = "17:31";

    /**
     * 打卡账号
     * */
    @Key
    private String username = "jzhou";

    /**
     * 打卡密码
     * */
    @Key
    private String password = "easipass";

    /**
     * 打卡标识
     *
     * 1: 开启
     * 0: 关闭
     * */
    @Key
    private String sign = "1";

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
     * getter
     * */
    public List<String> getDate() {
        return date;
    }

    public List<String> getWeek() {
        return week;
    }

    public String getToWorkTime() {
        return toWorkTime;
    }

    public String getOffWorkTime() {
        return offWorkTime;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSign() {
        return sign;
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static DaKaConfig getInstance() {
        return DA_KA_CONFIG;
    }

}