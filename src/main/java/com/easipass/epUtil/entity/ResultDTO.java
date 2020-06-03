package com.easipass.epUtil.entity;

import com.zj0724.springbootUtil.annotation.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ResultDTO {

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

    public ResultDTO() {}

    public ResultDTO(String channel, String note) {
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
        return "ResultDTO{" +
                "channel='" + channel + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

}