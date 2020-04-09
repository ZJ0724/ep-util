package com.easipass.EpUtilServer.service.impl.formResult;

import com.easipass.EpUtilServer.config.Sftp83Config;
import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.entity.Log;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.entity.Sftp;
import com.easipass.EpUtilServer.service.FormResultService;
import com.easipass.EpUtilServer.util.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pers.ZJ.UiAuto.Step;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.List;

@Service("tongXun")
public class TongXunFormResultServiceImpl implements FormResultService {

    @Resource
    private Sftp83Config sftp83Config;

    @Resource
    @Qualifier("yeWu")
    private FormResultService formResultService;

    @Override
    public Response upload(String ediNo, ResultDTO formResultDTO, boolean isDisposable, Sftp sftp, Step step) {
        Log.info("<---- 上传报关单通讯回执 ---->");

        if (!isDisposable) {
            //连接sftp
            sftp = new Sftp(
                    sftp83Config.getUrl(),
                    sftp83Config.getPort(),
                    sftp83Config.getUsername(),
                    sftp83Config.getPassword()
            );
            if (!sftp.connect()) {
                Log.info("sftp连接失败");
                return Response.returnFalse("", "sftp连接失败");
            }
        }

        //获取回执原document
        Document document = XmlUtil.getDocument(TongXunFormResultServiceImpl.class.getResourceAsStream("/formResult/tongXunFormResult"));

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
        dataDocumentRootElement.element("ResponseCode").setText(formResultDTO.getChannel());
        dataDocumentRootElement.element("ErrorMessage").setText(formResultDTO.getNote());
        dataDocumentRootElement.element("ClientSeqNo").setText(ediNo);
        dataDocumentRootElement.element("SeqNo").setText(ResultDTO.getSeqNo(ediNo));
        data = dataDocument.asXML();
        Log.info(data);

        //加密
        data=Base64Util.encode(data);

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);

        //上传到sftp
        sftp.uploadFile(
                sftp83Config.getUploadPath(),
                "tongXunFormResult-" + ResultDTO.getSeqNo(ediNo) + "-" + DateUtil.getTime(),
                new ByteArrayInputStream(document.asXML().getBytes())
        );

        if (!isDisposable) {
            // 关闭sftp
            sftp.close();

            // run
            EPMSUtil.run();
        } else {
            EPMSUtil.runOfStep(step);
        }

        Log.info("<---- success ---->");
        return Response.returnTrue(null);
    }

    @Override
    public Response disposableUpload(String ediNo, ResultDTO formResultDTO) {
        return formResultService.disposableUpload(ediNo, formResultDTO);
    }

    @Override
    public Response uploadMore(String ediNo, List<ResultDTO> resultDTOS) {
        return formResultService.uploadMore(ediNo, resultDTOS);
    }

}
