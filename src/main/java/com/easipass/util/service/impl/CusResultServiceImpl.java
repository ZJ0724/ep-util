package com.easipass.util.service.impl;

import com.easipass.util.config.BaseConfig;
import com.easipass.util.entity.cusresult.CustomsDeclarationCusResult;
import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.service.ConfigService;
import com.easipass.util.service.CusResultService;
import com.easipass.util.util.SWGDDatabaseUtil;
import com.easipass.util.util.SftpUtil;
import com.zj0724.common.component.Sftp;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.Base64Util;
import com.zj0724.common.util.DateUtil;
import com.zj0724.common.util.StringUtil;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.webDriver.ChromeWebDriver;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public final class CusResultServiceImpl implements CusResultService {

    @Resource
    private ConfigService configService;

    @Override
    public void uploadCustomsDeclaration(CustomsDeclarationCusResult customsDeclarationCusResult) {
        // 参数校验
        if (customsDeclarationCusResult == null) {
            throw new InfoException("回执信息不能为空");
        }
        String customsDeclarationNumber = customsDeclarationCusResult.getCustomsDeclarationNumber();
        String code = customsDeclarationCusResult.getCode();
        String note = customsDeclarationCusResult.getNote() == null ? "" : customsDeclarationCusResult.getNote();
        if (StringUtil.isEmpty(customsDeclarationNumber)) {
            throw new InfoException("报关单号不能为空");
        }
        if (StringUtil.isEmpty(code)) {
            throw new InfoException("回执代码不能为空");
        }

        // sftp
        Sftp uploadCusResultSftp = SftpUtil.getUploadCusResultSftp();
        // 谷歌驱动
        WebDriver webDriver = new ChromeWebDriver(BaseConfig.CHROME_DRIVER);

        try {
            // 报关单表头
            List<Map<String, Object>> formHeadList = SWGDDatabaseUtil.query("SELECT SEQ_NO FROM T_SWGD_FORM_HEAD WHERE EDI_NO = '" + customsDeclarationNumber + "'");
            if (formHeadList.size() == 0) {
                throw new InfoException("报关单信息不存在");
            }
            if (formHeadList.size() > 1) {
                throw new InfoException("存在多个相同编号的报关单");
            }
            Map<String, Object> formHead = formHeadList.get(0);

            // 回执上传地址
            ConfigPO uploadPathConfigPO = configService.getByCode(ConfigPO.Code.UPLOAD_CUS_RESULT_SFTP_PATH);
            if (uploadPathConfigPO == null || StringUtil.isEmpty(uploadPathConfigPO.getData())) {
                throw new InfoException("上传回执路径未配置");
            }

            // seqNo
            Object seqNo = formHead.get("SEQ_NO");

            // 判断是否存在通讯回执
            if (StringUtil.isEmpty(seqNo)) {
                // seqNo
                seqNo = "seqNo00000000" + customsDeclarationNumber.substring(customsDeclarationNumber.length() - 5);
                // 回执数据
                String cusResultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<DecImportResponse xmlns=\"http://www.chinaport.gov.cn/dec\">\n" +
                        "\t<ResponseCode>0</ResponseCode>\n" +
                        "\t<ErrorMessage>通讯回执</ErrorMessage>\n" +
                        "\t<ClientSeqNo>" + customsDeclarationNumber + "</ClientSeqNo>\n" +
                        "\t<SeqNo>" + seqNo + "</SeqNo>\n" +
                        "\t<TrnPreId></TrnPreId>\n" +
                        "</DecImportResponse>";
                // 加密
                cusResultData = Base64Util.encode(cusResultData);

                cusResultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<DxpMsg xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns=\"http://www.chinaport.gov.cn/dxp\" ver=\"1.0\" Id=\"ID01\">\n" +
                        "\t<TransInfo>\n" +
                        "\t\t<CopMsgId></CopMsgId>\n" +
                        "\t\t<SenderId></SenderId>\n" +
                        "\t\t<ReceiverIds>\n" +
                        "\t\t\t<ReceiverId></ReceiverId>\n" +
                        "\t\t</ReceiverIds>\n" +
                        "\t\t<CreatTime>" + DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss") + "</CreatTime>\n" +
                        "\t\t<MsgType>DECCUSSH1</MsgType>\n" +
                        "\t</TransInfo>\n" +
                        "\t<Data>" + cusResultData + "</Data>\n" +
                        "\t<AddInfo>\n" +
                        "\t\t<FileName></FileName>\n" +
                        "\t\t<IcCard></IcCard>\n" +
                        "\t\t<BizKey>\n" +
                        "\t\t\t<Key name=\"RetType\"></Key>\n" +
                        "\t\t\t<Key name=\"DealFlag\"></Key>\n" +
                        "\t\t</BizKey>\n" +
                        "\t</AddInfo>\n" +
                        "</DxpMsg>";

                // 上传
                uploadCusResultSftp.upload(uploadPathConfigPO.getData(), "tongXunCusResult_" + customsDeclarationNumber, cusResultData);

                swgdRecvRun(webDriver);
            }

            // 上传业务回执
            formHead = SWGDDatabaseUtil.query("SELECT * FROM T_SWGD_FORM_HEAD WHERE EDI_NO = '" + customsDeclarationNumber + "'").get(0);

            // 报关单号
            String preEntryId = formHead.get("PRE_ENTRY_ID") == null ? "" : formHead.get("PRE_ENTRY_ID").toString();
            if (preEntryId.equals(formHead.get("EDI_NO").toString())) {
                String declPort = formHead.get("DECL_PORT") == null ? "" : formHead.get("DECL_PORT").toString();
                String ieFlag = formHead.get("IE_FLAG") == null ? "" : formHead.get("IE_FLAG").toString();
                preEntryId = declPort + "0000" + ieFlag + "0000" + customsDeclarationNumber.substring(customsDeclarationNumber.length() - 5);
            }

            String cusResultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<DEC_RESULT>\n" +
                    "\t<CUS_CIQ_NO>" + seqNo.toString() + "</CUS_CIQ_NO>\n" +
                    "\t<ENTRY_ID>" + preEntryId + "</ENTRY_ID>\n" +
                    "\t<NOTICE_DATE>" + DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss") + "</NOTICE_DATE>\n" +
                    "\t<CHANNEL>" + code + "</CHANNEL>\n" +
                    "\t<NOTE>" + note + "</NOTE>\n" +
                    "\t<CUSTOM_MASTER></CUSTOM_MASTER>\n" +
                    "\t<I_E_DATE></I_E_DATE>\n" +
                    "\t<D_DATE>" + DateUtil.format(new Date(), "yyyy-MM-dd") + "</D_DATE>\n" +
                    "</DEC_RESULT>";

            // 加密
            cusResultData = Base64Util.encode(cusResultData);

            cusResultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<DxpMsg xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns=\"http://www.chinaport.gov.cn/dxp\" ver=\"1.0\" Id=\"ID01\">\n" +
                    "\t<TransInfo>\n" +
                    "\t\t<CopMsgId></CopMsgId>\n" +
                    "\t\t<SenderId></SenderId>\n" +
                    "\t\t<ReceiverIds>\n" +
                    "\t\t\t<ReceiverId></ReceiverId>\n" +
                    "\t\t</ReceiverIds>\n" +
                    "\t\t<CreatTime>" + DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss") + "</CreatTime>\n" +
                    "\t\t<MsgType></MsgType>\n" +
                    "\t</TransInfo>\n" +
                    "\t<Data>" + cusResultData + "</Data>\n" +
                    "\t<AddInfo>\n" +
                    "\t\t<FileName></FileName>\n" +
                    "\t\t<IcCard></IcCard>\n" +
                    "\t</AddInfo>\n" +
                    "</DxpMsg>";

            // 上传
            uploadCusResultSftp.upload(uploadPathConfigPO.getData(), "yeWuCusResult_" + customsDeclarationNumber, cusResultData);

            swgdRecvRun(webDriver);
        } finally {
            uploadCusResultSftp.close();
            webDriver.close();
        }
    }

    private static void swgdRecvRun(WebDriver webDriver) {
        try {
            webDriver.open("http://192.168.120.83:9909/console");
            webDriver.findElementByCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(1) > td:nth-child(2) > input").sendKey("admin");
            webDriver.findElementByCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td:nth-child(2) > input").sendKey("admin");
            webDriver.findElementByCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(3) > td > button").click();
            webDriver.findElementByCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > div > div:nth-child(1) > table > tbody > tr > td > div > div:nth-child(2) > div > div:nth-child(2) > table > tbody > tr > td:nth-child(1) > img").click();
            webDriver.findElementByCssSelector("#gwt-uid-26 > span").click();
            webDriver.findElementByCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > div > div:nth-child(3) > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td > div > div > table > tbody > tr:nth-child(2) > td > div > div:nth-child(1) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(5) > td:nth-child(4) > table > tbody > tr > td:nth-child(3) > button").click();
        } catch (Exception e) {
            throw new InfoException(e.getMessage());
        }
    }

}