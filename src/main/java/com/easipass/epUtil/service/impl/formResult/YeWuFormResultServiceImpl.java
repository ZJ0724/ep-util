package com.easipass.epUtil.service.impl.formResult;

import com.easipass.epUtil.entity.ChromeDriver;
import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.result.formResult.YeWuFormResult;
import com.easipass.epUtil.entity.sftp.Sftp83;
import com.easipass.epUtil.service.FormResultService;
import org.springframework.stereotype.Service;

@Service("YeWuFormResultServiceImpl")
public class YeWuFormResultServiceImpl implements FormResultService {

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

}