package com.easipass.EP_Util_Server.entity.formResult;

import org.springframework.stereotype.Controller;

@Controller
public class YeWuFormResultBean {

    private String cus_ciq_no;
    private String entry_id;
    private String notice_date;
    private String channel;
    private String note;
    private String custom_master;
    private String i_e_date;
    private String d_date;

    public String getCus_ciq_no() {
        return cus_ciq_no;
    }

    public void setCus_ciq_no(String cus_ciq_no) {
        this.cus_ciq_no = cus_ciq_no;
    }

    public String getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(String entry_id) {
        this.entry_id = entry_id;
    }

    public String getNotice_date() {
        return notice_date;
    }

    public void setNotice_date(String notice_date) {
        this.notice_date = notice_date;
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

    public String getCustom_master() {
        return custom_master;
    }

    public void setCustom_master(String custom_master) {
        this.custom_master = custom_master;
    }

    public String getI_e_date() {
        return i_e_date;
    }

    public void setI_e_date(String i_e_date) {
        this.i_e_date = i_e_date;
    }

    public String getD_date() {
        return d_date;
    }

    public void setD_date(String d_date) {
        this.d_date = d_date;
    }

}