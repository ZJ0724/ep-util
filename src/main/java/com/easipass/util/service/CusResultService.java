package com.easipass.util.service;

import com.easipass.util.entity.cusresult.CustomsDeclarationCusResult;
import org.springframework.stereotype.Service;

@Service
public interface CusResultService {

    /**
     * 上传报关单回执
     *
     * @param customsDeclarationCusResult customsDeclarationCusResult
     * */
    void uploadCustomsDeclaration(CustomsDeclarationCusResult customsDeclarationCusResult);

}