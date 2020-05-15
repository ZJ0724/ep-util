package com.easipass.epUtil.test;

import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.service.DecModResultService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.annotation.Resource;

public class DecModResultTest extends BaseTest {

    @Resource
    @Qualifier("BaseDecModResultServiceImpl")
    private DecModResultService baseDecModResultService;

    @Resource
    @Qualifier("QPDecModResultServiceImpl")
    private DecModResultService QPDecModResultService;

    @Resource
    @Qualifier("YeWuDecModResultServiceImpl")
    private DecModResultService YeWuDecModResultService;

    @Test
    public void setFileName() {
        String preEntryId = "221700000000045771";

        super.assertResponse(baseDecModResultService.setFileName(preEntryId));
    }

    @Test
    public void uploadQP() {
        String preEntryId = "221700000000045771";
        ResultDTO resultDTO = new ResultDTO("0", "QP入库成功");

        super.assertResponse(QPDecModResultService.upload(preEntryId, resultDTO));
    }

    @Test
    public void uploadYeWu() {
        String preEntryId = "221700000000045771";
        ResultDTO resultDTO = new ResultDTO("S", "成功");

        super.assertResponse(YeWuDecModResultService.upload(preEntryId, resultDTO));
    }

}
