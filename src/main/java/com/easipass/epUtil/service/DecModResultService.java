package com.easipass.epUtil.service;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.ResultDTO;
import org.springframework.stereotype.Service;

@Service
public interface DecModResultService {

    /**
     * 设置修撤单回执文件名
     * */
    Response setFileName(String preEntryId);

    /**
     * 上传
     * */
    Response upload(String preEntryId, ResultDTO resultDTO);

}
