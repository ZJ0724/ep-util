package com.easipass.EpUtilServer.service.impl;

import com.easipass.EpUtilServer.config.Sftp83Config;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.entity.Sftp;
import com.easipass.EpUtilServer.service.BaseService;
import com.easipass.EpUtilServer.util.EPMSUtil;
import com.easipass.EpUtilServer.util.StepWebDriverUtil;
import com.zj0724.StepWebDriver.entity.StepWebDriver;
import org.dom4j.Document;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;

@Service
public class BaseServiceImpl implements BaseService {

    @Override
    public Response before(boolean isDisposable, Sftp sftp) {
        if (!isDisposable) {
            sftp = Sftp.getSftp83();
        }

        if (!sftp.isConnect()) {
            System.out.println("开启一个sftp");
            if (!sftp.connect()) {
                return Response.returnFalse("", "Sftp：" + sftp.getUrl() + " 连接失败");
            }
        }

        return Response.returnTrue(sftp);
    }

    @Override
    public Response after(Document document, String fileName, boolean isDisposable, Sftp sftp, StepWebDriver stepWebDriver) {
        //上传到sftp
        sftp.uploadFile(Sftp83Config.uploadPath, fileName, new ByteArrayInputStream(document.asXML().getBytes()));

        // run
        if (!isDisposable) {
            stepWebDriver = StepWebDriverUtil.getStepWebDriver();
            System.out.println("开启一个stepWebDriver");
            EPMSUtil.run(stepWebDriver);
            sftp.close();
            System.out.println("关闭一个sftp");
            stepWebDriver.close();
            System.out.println("关闭一个stepWebDriver");
        } else {
            EPMSUtil.run(stepWebDriver);
        }

        return Response.returnTrue(null);
    }

}
