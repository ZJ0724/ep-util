package com.easipass.EpUtilServer.entity.DTO;

import org.springframework.stereotype.Component;

@Component
public class UploadMoreDTO extends ResultDTO {

    private String ediNo;

    public UploadMoreDTO(){}

    public UploadMoreDTO(String ediNo, String channel, String note) {
        super(channel, note);
        this.ediNo = ediNo;
    }

    public String getEdiNo() {
        return ediNo;
    }

    public void setEdiNo(String ediNo) {
        this.ediNo = ediNo;
    }

}
