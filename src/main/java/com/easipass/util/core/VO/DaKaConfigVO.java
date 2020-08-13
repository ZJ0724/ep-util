package com.easipass.util.core.VO;

import com.easipass.util.core.Config.DaKaProperties;
import com.easipass.util.core.util.ClassUtil;
import java.util.List;

/**
 * 打卡配置VO
 *
 * @author ZJ
 * */
public final class DaKaConfigVO {

    /**
     * 打卡日期
     * */
    private List<String> date;

    /**
     * 打卡星期
     * */
    private List<String> week;

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
     * 构造函数
     * */
    public DaKaConfigVO() {
        ClassUtil.assemblyData(DaKaProperties.getInstance(), this);
    }

    /**
     * set, get
     * */
    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<String> getWeek() {
        return week;
    }

    public void setWeek(List<String> week) {
        this.week = week;
    }

    public String getToWorkTime() {
        return toWorkTime;
    }

    public void setToWorkTime(String toWorkTime) {
        this.toWorkTime = toWorkTime;
    }

    public String getOffWorkTime() {
        return offWorkTime;
    }

    public void setOffWorkTime(String offWorkTime) {
        this.offWorkTime = offWorkTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DaKaPropertiesVO{" +
                "date=" + date +
                ", week=" + week +
                ", toWorkTime='" + toWorkTime + '\'' +
                ", offWorkTime='" + offWorkTime + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}