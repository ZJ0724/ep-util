package com.easipass.util.core.entity;

import com.easipass.util.core.util.DateUtil;

/**
 * 任务
 *
 * @author ZJ
 * */
public final class Task {

    /**
     * id
     * */
    private Integer id;

    /**
     * 任务名
     * */
    private String name;

    /**
     * 任务状态
     *
     * 0：进行中
     * 1：已完成
     * */
    private String flag;

    /**
     * 创建时间
     * */
    private String createTime;

    /**
     * 结束时间
     * */
    private String endTime;

    /**
     * 信息
     * */
    private String message;

    /**
     * 起始id
     * */
    private static int nextId = 0;

    /**
     * 构造函数
     *
     * @param name 任务名
     * */
    public Task(String name) {
        this.id = ++nextId;
        this.name = name;
        this.flag = "0";
        this.createTime = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 任务结束
     *
     * @param message 信息
     * */
    public void end(String message) {
        this.flag = "1";
        this.endTime = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
        this.message = message;
    }

    /**
     * 是否在执行
     *
     * @return 在执行返回true
     * */
    public boolean isRun() {
        return "0".equals(this.flag);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}