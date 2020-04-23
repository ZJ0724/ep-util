package com.easipass.EpUtilServer.service.impl.formResult;

import com.easipass.EpUtilServer.config.ResourcePathConfig;
import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.entity.DTO.UploadMoreDTO;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.entity.Sftp;
import com.easipass.EpUtilServer.enumeration.ResponseFlagEnum;
import com.easipass.EpUtilServer.service.BaseService;
import com.easipass.EpUtilServer.service.FormResultService;
import com.easipass.EpUtilServer.util.Base64Util;
import com.easipass.EpUtilServer.util.DateUtil;
import com.easipass.EpUtilServer.util.XmlUtil;
import com.zj0724.StepWebDriver.entity.StepWebDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.List;

@Service("YeWuFormResultServiceImpl")
public class YeWuFormResultServiceImpl implements FormResultService {

    @Resource
    private BaseService baseService;

    @Resource
    @Qualifier("BaseFormResultServiceImpl")
    private FormResultService baseFormResultService;

    @Override
    public Response upload(String ediNo, ResultDTO formResultDTO, boolean isDisposable, Sftp sftp, StepWebDriver stepWebDriver) {
        // 前置操作
        Response response = baseService.before(isDisposable, sftp);
        if (response.getFlag().equals(ResponseFlagEnum.FALSE.getFlag())) {
            return response;
        }
        sftp = (Sftp) response.getData();

        //获取回执原document
        Document document = XmlUtil.getDocument(TongXunFormResultServiceImpl.class.getResourceAsStream(ResourcePathConfig.YE_WU_FORM_RESULT_PATH));

        //document根节点
        Element documentRootElement = document.getRootElement();

        //data节点数据
        String data = documentRootElement.element("Data").getText();

        //解码
        data = Base64Util.decode(data);

        //获取回执解码后的document
        Document dataDocument = XmlUtil.getDocument(new ByteArrayInputStream(data.getBytes()));

        //dataDocument根节点
        Element dataDocumentRootElement = dataDocument.getRootElement();

        //替换数据
        dataDocumentRootElement.element("CUS_CIQ_NO").setText(ResultDTO.getSeqNo(ediNo));
        dataDocumentRootElement.element("ENTRY_ID").setText(ResultDTO.getPreEntryId(ediNo));
        dataDocumentRootElement.element("NOTICE_DATE").setText("2019-03-14T22:49:37");
        dataDocumentRootElement.element("CHANNEL").setText(formResultDTO.getChannel());
        dataDocumentRootElement.element("NOTE").setText(formResultDTO.getNote());
        data = dataDocument.asXML();

        //加密
        data = Base64Util.encode(data);

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);

        // 后置操作
        return baseService.after(document, "yeWuFormResult-" + ResultDTO.getSeqNo(ediNo) + "-" + DateUtil.getTime(), isDisposable, sftp, stepWebDriver);
    }

    @Override
    public Response disposableUpload(String ediNo, ResultDTO formResultDTO) {
        return baseFormResultService.disposableUpload(ediNo, formResultDTO);
    }

    @Override
    public Response uploadMore(List<UploadMoreDTO> uploadMoreDTOS) {
        return baseFormResultService.uploadMore(uploadMoreDTOS);
    }

}
