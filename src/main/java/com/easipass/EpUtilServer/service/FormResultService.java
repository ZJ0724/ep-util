package com.easipass.EpUtilServer.service;

import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.entity.Sftp;
import com.zj0724.StepWebDriver.entity.StepWebDriver;
import org.springframework.stereotype.Service;

@Service
public interface FormResultService {

    /**
     * 上传回执
     * */
    Response upload(String ediNo, ResultDTO formResultDTO, boolean isDisposable, Sftp sftp, StepWebDriver stepWebDriver);

    /**
     * 一次性上传
     * */
    Response disposableUpload(String ediNo, ResultDTO formResultDTO);

}
