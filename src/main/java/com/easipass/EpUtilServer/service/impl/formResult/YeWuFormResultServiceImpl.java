package com.easipass.EpUtilServer.service.impl.formResult;

import com.easipass.EpUtilServer.annotation.UploadResultAnnotation;
import com.easipass.EpUtilServer.config.ResourcePathConfig;
import com.easipass.EpUtilServer.entity.ResultDTO;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.service.BaseService;
import com.easipass.EpUtilServer.service.FormResultService;
import com.easipass.EpUtilServer.util.Base64Util;
import com.easipass.EpUtilServer.util.DateUtil;
import com.easipass.EpUtilServer.util.XmlUtil;
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
        dataDocumentRootElement.element("NOTICE_DATE").setText("2019-03-14T22:49:37");
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
