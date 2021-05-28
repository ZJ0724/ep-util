package com.easipass.util.entity.po;

import com.easipass.util.entity.AbstractPO;
import com.zj0724.common.component.jdbc.AccessDatabaseJdbc.FieldType;

/**
 * 配置
 *
 * @author ZJ
 * */
@Table(name = "CONFIG")
public final class ConfigPO extends AbstractPO {

    @Column(name = "CODE", type = FieldType.VARCHAR)
    private String code;

    @Column(name = "NOTE", type = FieldType.VARCHAR)
    private String note;

    @Column(name = "DATA", type = FieldType.VARCHAR)
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public enum Code {

        SWGD_DATABASE_HOST("SWGD数据库地址"),

        SWGD_DATABASE_PORT("SWGD数据库端口"),

        SWGD_DATABASE_SID("SWGD数据库sid"),

        SWGD_DATABASE_USERNAME("SWGD数据库账号"),

        SWGD_DATABASE_PASSWORD("SWGD数据库密码"),

        UPLOAD_CUS_RESULT_SFTP_HOST("上传回执sftp地址"),

        UPLOAD_CUS_RESULT_SFTP_PORT("上传回执sftp端口"),

        UPLOAD_CUS_RESULT_SFTP_USERNAME("上传回执sftp账号"),

        UPLOAD_CUS_RESULT_SFTP_PASSWORD("上传回执sftp密码"),

        UPLOAD_CUS_RESULT_SFTP_PATH("上传回执sftp路径"),

        SELENIUM_SERVER("selenium服务地址");

        private final String note;

        Code(String note) {
            this.note = note;
        }

        public String getNote() {
            return note;
        }

        public static Code getByCode(String code) {
            for (Code code1 : Code.values()) {
                if (code1.name().equals(code)) {
                    return code1;
                }
            }
            return null;
        }

    }

}