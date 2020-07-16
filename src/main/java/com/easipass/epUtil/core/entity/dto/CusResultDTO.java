package com.easipass.epUtil.core.entity.dto;

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

    public CusResultDTO() {}

    public CusResultDTO(String channel, String note) {
        this.channel = channel;
        this.note = note;
    }

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