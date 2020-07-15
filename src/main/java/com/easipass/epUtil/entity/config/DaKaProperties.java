package com.easipass.epUtil.entity.config;

import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.resources.config.DaKaPropertiesResource;
import java.util.List;

/**
 * 打卡配置
 *
 * @author ZJ
 * */
public final class DaKaProperties extends Config {

    /**
     * 打卡日期
     * */
    @Key
    private List<String> date;

    /**
     * 打卡星期
     * */
    @Key
    private List<String> week;

    /**
     * 上班打卡时间
     * */
    @Key
    private String toWorkTime;

    /**
     * 下班打卡时间
     * */
    @Key
    private String offWorkTime;

    /**
     * 打卡账号
     * */
    @Key
    private String username;

    /**
     * 打卡密码
     * */
    @Key
    private String password;

    /**
     * 打卡标识
     *
     * 1: 开启
     * 0: 关闭
     * */
    @Key
    private String sign;

    /**
     * 单例
     * */
    private static final DaKaProperties DA_KA_PROPERTIES = new DaKaProperties();

    /**
     * 构造函数
     * */
    private DaKaProperties() {
        super("daKa.properties", DaKaPropertiesResource.getInstance());
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static DaKaProperties getInstance() {
        return DA_KA_PROPERTIES;
    }

    /**
     * 获取日期
     *
     * @return 日期
     * */
    public List<String> getDate() {
        return this.date;
    }

    /**
     * 设置日期
     *
     * @param date 日期
     * */
    public void setDate(List<String> date) {
        this.date = date;
        this.save();
    }

    /**
     * 获取星期
     *
     * @return 星期
     * */
    public List<String> getWeek() {
        return this.week;
    }

    /**
     * 设置星期
     *
     * @param week 星期
     * */
    public void setWeek(List<String> week) {
        this.week = week;
        this.save();
    }

    /**
     * 获取上班时间
     *
     * @return 上班时间
     * */
    public String getToWorkTime() {
        return this.toWorkTime;
    }

    /**
     * 设置上班时间
     *
     * @param toWorkTime 上班时间
     * */
    public void setToWorkTime(String toWorkTime) {
        this.toWorkTime = toWorkTime;
        this.save();
    }

    /**
     * 获取下班时间
     *
     * @return 下班时间
     * */
    public String getOffWorkTime() {
        return this.offWorkTime;
    }

    /**
     * 设置下班时间
     *
     * @param offWorkTime 下班时间
     * */
    public void setOffWorkTime(String offWorkTime) {
        this.offWorkTime = offWorkTime;
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     * */
    public String getUsername() {
        return this.username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     * */
    public void setUsername(String username) {
        this.username = username;
        this.save();
    }

    /**
     * 获取密码
     *
     * @return 密码
     * */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     * */
    public void setPassword(String password) {
        this.password = password;
        this.save();
    }

    /**
     * 获取标识
     *
     * @return 标识
     * */
    public String getSign() {
        return this.sign;
    }

    /**
     * 设置标识
     *
     * @param sign 标识
     * */
    public void setSign(String sign) {
        this.sign = sign;
        this.save();
    }

}