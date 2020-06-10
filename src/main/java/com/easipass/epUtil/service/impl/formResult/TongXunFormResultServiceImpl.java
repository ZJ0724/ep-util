package com.easipass.epUtil.service.impl.formResult;

import com.easipass.epUtil.module.ChromeDriver;
import com.easipass.epUtil.entity.dto.ResultDTO;
import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.result.FormResult;
import com.easipass.epUtil.entity.result.formResult.TongXunFormResult;
import com.easipass.epUtil.module.sftp.Sftp83;
import com.easipass.epUtil.service.FormResultService;
import org.springframework.stereotype.Service;

@Service("TongXunFormResultServiceImpl")
public class TongXunFormResultServiceImpl implements FormResultService {

    @Override
    public Response upload(String ediNo, ResultDTO formResultDTO) {
        FormResult formResult = new TongXunFormResult(ediNo, formResultDTO);

        Sftp83 sftp83 = new Sftp83();
        sftp83.connect();
        sftp83.uploadResult(formResult);
        sftp83.close();

        ChromeDriver chromeDriver = new ChromeDriver();
        chromeDriver.swgdRecvRun();
        chromeDriver.close();

        return Response.returnTrue();
    }

}