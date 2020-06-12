package com.easipass.epUtil.service;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.dto.ResultDTO;
import org.springframework.stereotype.Service;

@Service
public interface DecModResultService {

    /**
     * 上传
     * */
    Response upload(String preEntryId, ResultDTO resultDTO);

}