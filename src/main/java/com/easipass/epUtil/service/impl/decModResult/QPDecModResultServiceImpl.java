package com.easipass.epUtil.service.impl.decModResult;

import com.easipass.epUtil.annotation.UploadResult;
import com.easipass.epUtil.config.ResourcePathConfig;
import com.easipass.epUtil.entity.*;
import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.service.BaseService;
import com.easipass.epUtil.service.DecModResultService;
import com.easipass.epUtil.util.Base64Util;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;

@Service("QPDecModResultServiceImpl")
public class QPDecModResultServiceImpl implements DecModResultService {

    @Override
    @UploadResult
    public Response upload(String preEntryId, ResultDTO resultDTO) {
        // 设置文件名
        Response response = BaseDecModResultService.setFileName(preEntryId);
        if (!response.getFlag().equals("T")) {
            return response;
        }

        //获取回执原document
        Document document = XmlUtil.getDocument(QPDecModResultServiceImpl.class.getResourceAsStream(ResourcePathConfig.QP_DEC_MOD_RESULT_PATH));

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
        dataDocumentRootElement.element("DecModSeqNo").setText(ResultDTO.getDecModSeqNo(preEntryId));
        dataDocumentRootElement.element("ResultMessage").setText(resultDTO.getNote());
        dataDocumentRootElement.element("ResultCode").setText(resultDTO.getChannel());
        data = dataDocument.asXML();

        //加密
        data = Base64Util.encode(data);

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);
        //替换FileName
        documentRootElement.element("AddInfo").element("FileName").setText(ResultDTO.getFileName(preEntryId));

        BaseService.uploadResult(document, "QPDecModResult-" + ResultDTO.getDecModSeqNo(preEntryId) + "-" + DateUtil.getTime());

        return Response.returnTrue(null);
    }

}
