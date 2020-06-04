package com.easipass.epUtil.service;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.ResultDTO;
import org.springframework.stereotype.Service;

@Service
public interface DisposableUploadService {

    /**
     * 一次性上传报关单回执
     * */
    Response formResult(String ediNo, ResultDTO formResultDTO);

    /**
     * 一次性上传修撤单回执
     * */
    Response decModResult(String preEntryId, ResultDTO formResultDTO);

}