package com.easipass.epUtil.service;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.ResultDTO;
import org.springframework.stereotype.Service;

@Service("DisposableUploadService")
public interface DisposableUploadService {

    /**
     * 一次性上传
     * */
    Response disposableUpload(String ediNo, ResultDTO formResultDTO);

}