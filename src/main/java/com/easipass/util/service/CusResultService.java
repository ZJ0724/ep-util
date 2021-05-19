package com.easipass.util.service;

import com.easipass.util.entity.CusResult;
import org.springframework.stereotype.Service;

@Service
public interface CusResultService {

    /**
     * 上传报关单回执
     *
     * @param customsDeclarationNumber 报关单号
     * @param tongXunCusResult 通讯回执
     * @param yeWuCusResult 业务回执
     * */
    void uploadCustomsDeclaration(String customsDeclarationNumber, CusResult tongXunCusResult, CusResult yeWuCusResult);

}