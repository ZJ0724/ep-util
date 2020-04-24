package com.easipass.EpUtilServer.test;

import com.easipass.EpUtilServer.entity.ResultDTO;
import com.easipass.EpUtilServer.service.FormResultService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.annotation.Resource;

public class FormResultTest extends BaseTest {

    @Resource
    @Qualifier("BaseFormResultServiceImpl")
    private FormResultService baseFormResultService;

    @Resource
    @Qualifier("TongXunFormResultServiceImpl")
    private FormResultService tongXunFormResultService;

    @Resource
    @Qualifier("YeWuFormResultServiceImpl")
    private FormResultService yeWuFormResultService;

    @Test
    public void uploadTongXun() {
        String ediNo = "EDI200000001745812";
        ResultDTO resultDTO = new ResultDTO("0", "备注");

        super.assertResponse(tongXunFormResultService.upload(ediNo, resultDTO));
    }

    @Test
    public void uploadYeWu() {
        String ediNo = "EDI200000001745812";
        ResultDTO resultDTO = new ResultDTO("P", "业务回执");

        super.assertResponse(yeWuFormResultService.upload(ediNo, resultDTO));
    }

    @Test
    public void disposableUpload() {
        String ediNo = "EDI200000001745812";
        ResultDTO resultDTO = new ResultDTO("P", "业务回执");

        super.assertResponse(baseFormResultService.disposableUpload(ediNo, resultDTO));
    }

}
