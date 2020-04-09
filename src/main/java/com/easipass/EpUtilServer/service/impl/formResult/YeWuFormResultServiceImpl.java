package com.easipass.EpUtilServer.service.impl.formResult;

import com.easipass.EpUtilServer.config.Sftp83Config;
import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.entity.Log;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.entity.Sftp;
import com.easipass.EpUtilServer.exception.ErrorException;
import com.easipass.EpUtilServer.service.FormResultService;
import com.easipass.EpUtilServer.util.Base64Util;
import com.easipass.EpUtilServer.util.DateUtil;
import com.easipass.EpUtilServer.util.EPMSUtil;
import com.easipass.EpUtilServer.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pers.ZJ.UiAuto.Step;
import pers.ZJ.UiAuto.step.UiStep;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.List;

@Service("yeWu")
public class YeWuFormResultServiceImpl implements FormResultService {

    @Resource
    private Sftp83Config sftp83Config;

    @Resource
    @Qualifier("tongXun")
    private FormResultService formResultService;

    @Override
    public Response upload(String ediNo, ResultDTO formResultDTO, boolean isDisposable, Sftp sftp, Step step) {
        Log.info("<---- 上传报关单业务回执 ---->");

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
        Document document = XmlUtil.getDocument(TongXunFormResultServiceImpl.class.getResourceAsStream("/formResult/yeWuFormResult"));

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
        Log.info(data);

        //加密
        data = Base64Util.encode(data);

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);

        //上传到sftp
        sftp.uploadFile(
                sftp83Config.getUploadPath(),
                "yeWuFormResult-" + ResultDTO.getSeqNo(ediNo) + "-" + DateUtil.getTime(),
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
        // sftp
        Sftp sftp = new Sftp(
                sftp83Config.getUrl(),
                sftp83Config.getPort(),
                sftp83Config.getUsername(),
                sftp83Config.getPassword()
        );
        sftp.connect();

        // step
        Step step = new UiStep(EPMSUtil.getChromeDriverFile());

        // 通讯回执信息
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setChannel("0");
        resultDTO.setNote("通讯回执备注");

        Response response = formResultService.upload(ediNo, resultDTO, true, sftp, step);

        if (response.getFlag().equals("T")) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new ErrorException(e.getMessage());
            }
            Response response1 = this.upload(ediNo, formResultDTO, true, sftp, step);
            // 关闭sftp
            sftp.close();

            // 关闭step
            step.closeWebDriver();

            return response1;
        } else {
            return response;
        }
    }

    @Override
    public Response uploadMore(String ediNo, List<ResultDTO> resultDTOS) {
        // sftp
        Sftp sftp = new Sftp(
                sftp83Config.getUrl(),
                sftp83Config.getPort(),
                sftp83Config.getUsername(),
                sftp83Config.getPassword()
        );
        sftp.connect();

        // step
        Step step = new UiStep(EPMSUtil.getChromeDriverFile());

        // 通讯回执信息
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setChannel("0");
        resultDTO.setNote("通讯回执备注");

        Response response = formResultService.upload(ediNo, resultDTO, true, sftp, step);

        if (response.getFlag().equals("T")) {
            for (ResultDTO resultDTO1 : resultDTOS) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new ErrorException(e.getMessage());
                }
                Response response1 = this.upload(ediNo, resultDTO1, true, sftp, step);
                if (!response1.getFlag().equals("T")) {
                    return response1;
                }
            }
            // 关闭sftp
            sftp.close();

            // 关闭step
            step.closeWebDriver();

            return Response.returnTrue(null);
        } else {
            return response;
        }
    }

}
