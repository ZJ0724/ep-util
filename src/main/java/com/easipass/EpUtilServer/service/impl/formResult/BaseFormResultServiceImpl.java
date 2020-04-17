package com.easipass.EpUtilServer.service.impl.formResult;

import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.entity.Sftp;
import com.easipass.EpUtilServer.enumeration.ResponseEnum;
import com.easipass.EpUtilServer.exception.ErrorException;
import com.easipass.EpUtilServer.service.FormResultService;
import com.easipass.EpUtilServer.util.StepWebDriverUtil;
import com.zj0724.StepWebDriver.entity.StepWebDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
@Qualifier("BaseFormResultServiceImpl")
public class BaseFormResultServiceImpl implements FormResultService {

    @Resource
    @Qualifier("TongXunFormResultServiceImpl")
    private FormResultService tongXun;

    @Resource
    @Qualifier("YeWuFormResultServiceImpl")
    private FormResultService yeWu;

    @Override
    public Response upload(String ediNo, ResultDTO formResultDTO, boolean isDisposable, Sftp sftp, StepWebDriver stepWebDriver) {
        throw ErrorException.getErrorException();
    }

    @Override
    public Response disposableUpload(String ediNo, ResultDTO resultDTO) {
        Sftp sftp = Sftp.getSftp83();
        StepWebDriver stepWebDriver = StepWebDriverUtil.getStepWebDriver();

        // 通讯回执
        Response response = tongXun.upload(ediNo, new ResultDTO("0", "通讯回执上传成功"), true, sftp, stepWebDriver);
        if (response.getFlag().equals(ResponseEnum.FALSE.getFlag())) {
            return response;
        }

        // 等待500ms
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }

        // 业务回执
        response = yeWu.upload(ediNo, resultDTO, true, sftp, stepWebDriver);

        sftp.close();
        stepWebDriver.close();

        return response;
    }

}
