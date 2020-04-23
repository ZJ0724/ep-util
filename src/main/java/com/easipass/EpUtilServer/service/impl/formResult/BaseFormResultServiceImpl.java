package com.easipass.EpUtilServer.service.impl.formResult;

import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.entity.DTO.UploadMoreDTO;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.entity.Sftp;
import com.easipass.EpUtilServer.enumeration.ResponseFlagEnum;
import com.easipass.EpUtilServer.exception.ErrorException;
import com.easipass.EpUtilServer.service.FormResultService;
import com.easipass.EpUtilServer.util.StepWebDriverUtil;
import com.zj0724.StepWebDriver.entity.StepWebDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

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
        if (response.getFlag().equals(ResponseFlagEnum.FALSE.getFlag())) {
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

    @Override
    public Response uploadMore(List<UploadMoreDTO> uploadMoreDTOS) {
        Response response = null;
        Sftp sftp = Sftp.getSftp83();
        StepWebDriver stepWebDriver = StepWebDriverUtil.getStepWebDriver();
        int index = 0;

        for (UploadMoreDTO uploadMoreDTO : uploadMoreDTOS) {
            // 上传通讯回执
            response = tongXun.upload(uploadMoreDTO.getEdiNo(), new ResultDTO("0", "通讯回执上传成功"), true, sftp, stepWebDriver);
            if (response.getFlag().equals(ResponseFlagEnum.FALSE.getFlag())) {
                return response;
            }

            // 等待500ms
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw ErrorException.getErrorException(e.getMessage());
            }

            // 上传业务回执
            response = yeWu.upload(uploadMoreDTO.getEdiNo(), new ResultDTO(uploadMoreDTO.getChannel(), uploadMoreDTO.getNote()), true, sftp, stepWebDriver);

            // 等待500ms
            if (index++ != uploadMoreDTOS.size()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw ErrorException.getErrorException(e.getMessage());
                }
            }
        }

        sftp.close();
        stepWebDriver.close();
        return response;
    }

}
