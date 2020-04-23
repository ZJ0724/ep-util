package com.easipass.EpUtilServer.service;

import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.entity.DTO.UploadMoreDTO;
import com.easipass.EpUtilServer.entity.Response;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface FormResultService {

    /**
     * 上传回执
     * */
    Response upload(String ediNo, ResultDTO formResultDTO);

    /**
     * 一次性上传
     * */
    Response disposableUpload(String ediNo, ResultDTO formResultDTO);

    /**
     * 多份报关单上传回执
     * */
    Response uploadMore(List<UploadMoreDTO> uploadMoreDTOS);

}
