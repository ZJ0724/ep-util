package com.easipass.EpUtilServer.service.impl.formResult;

import com.easipass.EpUtilServer.annotation.UploadResultAnnotation;
import com.easipass.EpUtilServer.entity.ResultDTO;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.enumeration.ResponseFlagEnum;
import com.easipass.EpUtilServer.exception.ErrorException;
import com.easipass.EpUtilServer.service.FormResultService;
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
    public Response upload(String ediNo, ResultDTO formResultDTO) {
        throw ErrorException.getErrorException();
    }

    @Override
    @UploadResultAnnotation(isDisposable = true)
    public Response disposableUpload(String ediNo, ResultDTO resultDTO) {
        // 通讯回执
        Response response = tongXun.upload(ediNo, new ResultDTO("0", "通讯回执上传成功"));
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
        yeWu.upload(ediNo, resultDTO);

        return Response.returnTrue(null);
    }

}
