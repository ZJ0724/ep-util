package com.easipass.epUtil.service;

import com.easipass.epUtil.entity.dto.ResultDTO;
import com.easipass.epUtil.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface FormResultService {

    /**
     * 上传回执
     * */
    Response upload(String ediNo, ResultDTO formResultDTO);

}