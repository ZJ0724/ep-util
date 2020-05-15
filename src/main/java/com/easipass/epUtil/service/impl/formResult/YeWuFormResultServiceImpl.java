package com.easipass.epUtil.service.impl.formResult;

import com.easipass.epUtil.annotation.UploadResultAnnotation;
import com.easipass.epUtil.config.ResourcePathConfig;
import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.service.BaseService;
import com.easipass.epUtil.service.FormResultService;
import com.easipass.epUtil.util.Base64Util;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;

@Service("YeWuFormResultServiceImpl")
public class YeWuFormResultServiceImpl implements FormResultService {

    @Resource
    @Qualifier("BaseFormResultServiceImpl")
    private FormResultService baseFormResultService;

    @Override
    @UploadResultAnnotation
    public Response upload(String ediNo, ResultDTO formResultDTO) {
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
        dataDocumentRootElement.element("NOTICE_DATE").setText(DateUtil.getDate());
        dataDocumentRootElement.element("CHANNEL").setText(formResultDTO.getChannel());
        dataDocumentRootElement.element("NOTE").setText(formResultDTO.getNote());
        data = dataDocument.asXML();

        //加密
        data = Base64Util.encode(data);

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);

        BaseService.uploadResult(document, "yeWuFormResult-" + ResultDTO.getSeqNo(ediNo) + "-" + DateUtil.getTime());
        return Response.returnTrue(null);
    }

    @Override
    public Response disposableUpload(String ediNo, ResultDTO formResultDTO) {
        return baseFormResultService.disposableUpload(ediNo, formResultDTO);
    }

}
