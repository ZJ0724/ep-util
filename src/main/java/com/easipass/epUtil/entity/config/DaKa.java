package com.easipass.epUtil.entity.config;

import java.util.ArrayList;
import java.util.List;

public class DaKa extends AbstractConfig {

    /**
     * 打卡日期
     * */
    private final List<String> date = new ArrayList<>();

    /**
     * 打卡星期
     * */
    private final List<String> week = new ArrayList<>();

    /**
     * 上班打卡时间
     * */
    private String toWorkTime;

    /**
     * 下班打卡时间
     * */
    private String offWorkTime;

    /**
     * 打卡账号
     * */
    private String username;

    /**
     * 打卡密码
     * */
    private String password;

    /**
     * 单例
     * */
    private final static DaKa daKa = new DaKa();

    /**
     * 构造函数
     * */
    private DaKa() {}

    /**
     * get
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

    /**
     * 获取单例
     * */
    public static DaKa getDaKa() {
        return daKa;
    }

    @Override
    public String toString() {
        return "DaKa{" +
                "date=" + date +
                ", week=" + week +
                ", toWorkTime='" + toWorkTime + '\'' +
                ", offWorkTime='" + offWorkTime + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}