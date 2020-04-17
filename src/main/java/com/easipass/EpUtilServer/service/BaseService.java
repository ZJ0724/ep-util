package com.easipass.EpUtilServer.service;

import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.entity.Sftp;
import com.zj0724.StepWebDriver.entity.StepWebDriver;
import org.dom4j.Document;
import org.springframework.stereotype.Service;

@Service
public interface BaseService {

    /**
     * 前置操作
     * */
    Response before(boolean isDisposable, Sftp sftp);

    /**
     * 后置操作
     * */
    Response after(Document document, String fileName, boolean isDisposable, Sftp sftp, StepWebDriver stepWebDriver);

}
