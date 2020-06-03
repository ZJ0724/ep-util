package com.easipass.epUtil.service.impl.formResult;

import com.easipass.epUtil.entity.ChromeDriver;
import com.easipass.epUtil.entity.Result;
import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.result.formResult.TongXunFormResult;
import com.easipass.epUtil.entity.result.formResult.YeWuFormResult;
import com.easipass.epUtil.entity.sftp.Sftp83;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.service.DisposableUploadService;
import com.easipass.epUtil.service.FormResultService;
import org.springframework.stereotype.Service;

@Service("YeWuFormResultServiceImpl")
public class YeWuFormResultServiceImpl implements FormResultService, DisposableUploadService {

    @Override
    public Response upload(String ediNo, ResultDTO formResultDTO) {
        YeWuFormResult yeWuFormResult = new YeWuFormResult(ediNo, formResultDTO);

        Sftp83 sftp83 = new Sftp83();
        sftp83.connect();
        sftp83.uploadResult(yeWuFormResult);
        sftp83.close();

        ChromeDriver chromeDriver = new ChromeDriver();
        chromeDriver.swgdRecvRun();
        chromeDriver.close();

        return Response.returnTrue();
    }

    @Override
    public Response disposableUpload(String ediNo, ResultDTO formResultDTO) {
        // 先上传通讯回执
        Result tongXun = new TongXunFormResult(ediNo, new ResultDTO("0", "通讯回执上传成功"));

        Sftp83 sftp83 = new Sftp83();
        sftp83.connect();
        sftp83.uploadResult(tongXun);

        ChromeDriver chromeDriver = new ChromeDriver();
        chromeDriver.swgdRecvRun();

        // 等待500ms
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }

        // 再上传业务回执
        Result yeWu = new YeWuFormResult(ediNo, formResultDTO);
        sftp83.uploadResult(yeWu);
        chromeDriver.swgdRecvRun();
        sftp83.close();
        chromeDriver.close();

        return Response.returnTrue();
    }

}