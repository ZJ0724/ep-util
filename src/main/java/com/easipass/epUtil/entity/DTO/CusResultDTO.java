package com.easipass.epUtil.entity.DTO;

import com.zj0724.springbootUtil.annotation.NotNull;

/**
 * 回执DTO
 *
 * @author ZJ
 * */
public class CusResultDTO {

    /**
     * 状态
     * */
    @NotNull
    private String channel;

    /**
     * 备注
     * */
    @NotNull
    private String note;

    /**
     * 构造函数
     * */
    public CusResultDTO() {}

    /**
     * 构造函数
     *
     * @param channel 状态
     * @param note 备注
     * */
    public CusResultDTO(String channel, String note) {
        this.channel = channel;
        this.note = note;
    }

    /**
     * set, get
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

    @Override
    public String toString() {
        return "CusResultDTO{" +
                "channel='" + channel + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

}