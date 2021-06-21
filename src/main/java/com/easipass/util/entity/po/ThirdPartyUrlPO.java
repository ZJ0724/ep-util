package com.easipass.util.entity.po;

import com.easipass.util.entity.AbstractPO;
import com.zj0724.common.component.jdbc.AccessDatabaseJdbc;

@Table(name = "THIRD_PARTY_URL")
public final class ThirdPartyUrlPO extends AbstractPO {

    @Column(name = "URL", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String url;

    @Column(name = "NOTE", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String note;

    @Column(name = "REQUEST_HEADER", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String requestHeader;

    @Column(name = "REQUEST_DATA", type = AccessDatabaseJdbc.FieldType.LONG_VARCHAR)
    private String requestData;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

}