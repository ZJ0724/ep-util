package com.easipass.epUtil.entity.config;

import com.alibaba.fastjson.JSONObject;
import com.easipass.epUtil.exception.ErrorException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Swgd {

    /**
     * 地址
     * */
    private String url;

    /**
     * 端口
     * */
    private Integer port;

    /**
     * sid
     * */
    private String sid;

    /**
     * 用户名
     * */
    private String username;

    /**
     * 密码
     * */
    private String password;

    /**
     * 单例
     */
    public final static Swgd SWGD = new Swgd();

    /**
     * 构造函数
     * */
    private Swgd() {}

    /**
     * set,get
     * */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    /**
     * 获取单例
     * */
    public static Swgd getSWGD() {
        return SWGD;
    }

    /**
     * 加载数据
     * */
    public void loadData(JSONObject jsonObject) {
        // 所有字段
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            String fieldName = field.getName();
            try {
                field.set(this, jsonObject.get(fieldName));
            } catch (IllegalAccessException e) {
                throw ErrorException.getErrorException(e.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return "Swgd{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", sid='" + sid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}