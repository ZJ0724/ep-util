package com.easipass.epUtil.service.impl.decModResult;

import com.easipass.epUtil.entity.*;
import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.entity.result.decModResult.QPDecModResult;
import com.easipass.epUtil.entity.sftp.Sftp83;
import com.easipass.epUtil.service.DecModResultService;
import org.springframework.stereotype.Service;

@Service("QPDecModResultServiceImpl")
public class QPDecModResultServiceImpl implements DecModResultService {

    @Override
    public Response upload(String preEntryId, ResultDTO resultDTO) {
        Result result = new QPDecModResult(preEntryId, resultDTO);

        Sftp83 sftp83 = new Sftp83();
        sftp83.connect();
        sftp83.uploadResult(result);
        sftp83.close();

        ChromeDriver chromeDriver = new ChromeDriver();
        chromeDriver.swgdRecvRun();
        chromeDriver.close();

        return Response.returnTrue();
    }

}
