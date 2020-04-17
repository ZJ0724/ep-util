package com.easipass.EpUtilServer.service.impl.decModResult;

import com.easipass.EpUtilServer.config.ResourcePathConfig;
import com.easipass.EpUtilServer.entity.*;
import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.enumeration.ResponseEnum;
import com.easipass.EpUtilServer.service.BaseService;
import com.easipass.EpUtilServer.service.DecModResultService;
import com.easipass.EpUtilServer.util.Base64Util;
import com.easipass.EpUtilServer.util.DateUtil;
import com.easipass.EpUtilServer.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;

@Service("QPDecModResultServiceImpl")
public class QPDecModResultServiceImpl implements DecModResultService {

    @Resource
    @Qualifier("BaseDecModResultServiceImpl")
    private DecModResultService baseDecModResultService;

    @Resource
    private BaseService baseService;

    @Override
    public Response setFileName(String preEntryId) {
        return baseDecModResultService.setFileName(preEntryId);
    }

    @Override
    public Response upload(String preEntryId, ResultDTO resultDTO) {
        // 前置操作
        Response response = baseService.before(false, null);
        if (response.getFlag().equals(ResponseEnum.FALSE.getFlag())) {
            return response;
        }
        Sftp sftp = (Sftp) response.getData();

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

        // 后置操作
        return baseService.after(document, "QPDecModResult-" + ResultDTO.getDecModSeqNo(preEntryId) + "-" + DateUtil.getTime(), false, sftp, null);
    }

}
