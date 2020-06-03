package com.easipass.epUtil.entity;

public abstract class Result {

    /**
     * 状态
     * */
    private String channel;

    /**
     * 备注
     * */
    private String note;

    /**
     * 回执全内容
     */
    private String data;

    /**
     * 文件名
     * */
    private String fileName;

    /**
     * 构造函数
     * */
    protected Result(ResultDTO resultDTO) {
        this.channel = resultDTO.getChannel();
        this.note = resultDTO.getNote();
    }

    /**
     * 初始化
     * */
    protected void init() {
        this.fileName = this.makeFileName();
        this.data = this.makeData();
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 生成回执全内容
     */
    public abstract String makeData();

    /**
     * 生成文件名
     * */
    public abstract String makeFileName();

    @Override
    public String toString() {
        return "Result{" +
                ", channel='" + channel + '\'' +
                ", note='" + note + '\'' +
                ", data='" + data + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }

}