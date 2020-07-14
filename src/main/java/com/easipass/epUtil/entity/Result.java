package com.easipass.epUtil.entity;

import com.easipass.epUtil.entity.dto.ResultDTO;

public abstract class Result {

    /**
     * 状态
     * */
    private final String channel;

    /**
     * 备注
     * */
    private final String note;

    /**
     * 构造函数
     *
     * @param resultDTO
     * */
    protected Result(ResultDTO resultDTO) {
        this.channel = resultDTO.getChannel();
        this.note = resultDTO.getNote();
    }

    /**
     * set,get
     * */
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * 生成回执全内容
     */
    public abstract String makeData();

    /**
     * 获取文件名
     *
     * @return 文件名
     * */
    public abstract String getFileName();

    @Override
    public String toString() {
        return "Result{" +
                "channel='" + channel + '\'' +
                ", note='" + note + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

}