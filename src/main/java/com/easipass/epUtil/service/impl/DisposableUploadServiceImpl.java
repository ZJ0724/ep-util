package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.entity.*;
import com.easipass.epUtil.entity.result.decModResult.QPDecModResult;
import com.easipass.epUtil.entity.result.decModResult.YeWuDecModResult;
import com.easipass.epUtil.entity.result.formResult.TongXunFormResult;
import com.easipass.epUtil.entity.result.formResult.YeWuFormResult;
import com.easipass.epUtil.entity.sftp.Sftp83;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.exception.OracleException;
import com.easipass.epUtil.service.DisposableUploadService;
import org.springframework.stereotype.Service;

@Service
public class DisposableUploadServiceImpl implements DisposableUploadService {

    @Override
    public Response formResult(String ediNo, ResultDTO formResultDTO) {
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
        Result yeWu = null;

        // 可能查不到报关单数据
        try {
            yeWu = new YeWuFormResult(ediNo, formResultDTO);
        } catch (OracleException e) {
            Log.getLog().error(e.getMessage());

            // 关闭sftp和谷歌驱动
            sftp83.close();
            chromeDriver.close();

            return Response.returnFalse(e.getMessage());
        }

        sftp83.uploadResult(yeWu);
        chromeDriver.swgdRecvRun();
        sftp83.close();
        chromeDriver.close();

        return Response.returnTrue();
    }

    @Override
    public Response decModResult(String preEntryId, ResultDTO formResultDTO) {
        // 先上传通讯回执
        Result QP = new QPDecModResult(preEntryId, new ResultDTO("0", "QP回执上传成功"));

        Sftp83 sftp83 = new Sftp83();
        sftp83.connect();
        sftp83.uploadResult(QP);

        ChromeDriver chromeDriver = new ChromeDriver();
        chromeDriver.swgdRecvRun();

        // 等待500ms
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }

        // 再上传业务回执
        Result yeWu = null;

        // 可能查不到数据
        try {
            yeWu = new YeWuDecModResult(preEntryId, formResultDTO);
        } catch (OracleException e) {
            Log.getLog().error(e.getMessage());

            // 关闭sftp和谷歌驱动
            sftp83.close();
            chromeDriver.close();

            return Response.returnFalse(e.getMessage());
        }

        sftp83.uploadResult(yeWu);
        chromeDriver.swgdRecvRun();
        sftp83.close();
        chromeDriver.close();

        return Response.returnTrue();
    }

}