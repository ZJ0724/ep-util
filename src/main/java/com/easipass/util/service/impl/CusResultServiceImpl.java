package com.easipass.util.service.impl;

import com.easipass.util.config.BaseConfig;
import com.easipass.util.entity.CusResult;
import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.service.ConfigService;
import com.easipass.util.service.CusResultService;
import com.easipass.util.util.SWGDDatabaseUtil;
import com.easipass.util.util.SftpUtil;
import com.zj0724.common.component.ftp.Sftp;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.Base64Util;
import com.zj0724.common.util.DateUtil;
import com.zj0724.common.util.StringUtil;
import com.zj0724.uiAuto.Selector;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.webDriver.ChromeWebDriver;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service
public final class CusResultServiceImpl implements CusResultService {

    @Resource
    private ConfigService configService;

    @Override
    public void uploadCustomsDeclaration(String customsDeclarationNumber, CusResult tongXunCusResult, CusResult yeWuCusResult) {
        // 参数校验
        if (customsDeclarationNumber == null) {
            throw new InfoException("报关单号不能为空");
        }

        // 检查报关单数据是否存在
        if (SWGDDatabaseUtil.getFormHead(customsDeclarationNumber) == null) {
            throw new InfoException("报关单信息不存在");
        }

        // 回执上传地址
        ConfigPO uploadPathConfigPO = configService.getByCode(ConfigPO.Code.UPLOAD_CUS_RESULT_SFTP_PATH);
        if (uploadPathConfigPO == null || StringUtil.isEmpty(uploadPathConfigPO.getData())) {
            throw new InfoException("上传回执路径未配置");
        }
        String uploadPath = uploadPathConfigPO.getData();

        // sftp
        Sftp uploadCusResultSftp = SftpUtil.getUploadCusResultSftp();
        // 谷歌驱动
        WebDriver webDriver = new ChromeWebDriver(BaseConfig.CHROME_DRIVER);

        try {
            // 上传通讯回执
            if (tongXunCusResult != null) {
                uploadTongXunCustomsDeclaration(customsDeclarationNumber, tongXunCusResult, uploadCusResultSftp, webDriver, uploadPath);
            }

            // 上传业务回执
            if (yeWuCusResult != null) {
                uploadYeWuCustomsDeclaration(customsDeclarationNumber, yeWuCusResult, uploadCusResultSftp, webDriver, uploadPath);
            }
        } finally {
            uploadCusResultSftp.close();
            webDriver.close();
        }
    }

    /**
     * 上传报关单通讯回执
     *
     * @param customsDeclarationNumber 报关单号
     * @param cusResult cusResult
     * @param sftp sftp
     * @param webDriver webDriver
     * @param uploadPath 回执上传路径
     * */
    private static void uploadTongXunCustomsDeclaration(String customsDeclarationNumber, CusResult cusResult, Sftp sftp, WebDriver webDriver, String uploadPath) {
        // 获取报关单数据
        Map<String, Object> formHead = SWGDDatabaseUtil.getFormHead(customsDeclarationNumber);
        if (formHead == null) {
            throw new InfoException("报关单数据不存在");
        }

        // 默认的通讯回执
        if (cusResult == null) {
            cusResult = new CusResult();
            cusResult.setCode("0");
            cusResult.setNote("通讯回执");
        }

        // seqNo
        String seqNo = formHead.get("SEQ_NO") == null ? null : formHead.get("SEQ_NO").toString();
        if (StringUtil.isEmpty(seqNo)) {
            seqNo = "seqNo00000000" + customsDeclarationNumber.substring(customsDeclarationNumber.length() - 5);
        }

        // 回执数据
        String cusResultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<DecImportResponse xmlns=\"http://www.chinaport.gov.cn/dec\">\n" +
                "\t<ResponseCode>" + cusResult.getCode() + "</ResponseCode>\n" +
                "\t<ErrorMessage>" + cusResult.getNote() + "</ErrorMessage>\n" +
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
        sftp.upload(uploadPath, "tongXunCusResult_" + customsDeclarationNumber, cusResultData);
        swgdRecvRun(webDriver);
    }

    /**
     * 上传报关单业务回执
     *
     * @param customsDeclarationNumber 报关单号
     * @param cusResult cusResult
     * @param sftp sftp
     * @param webDriver webDriver
     * @param uploadPath 回执上传路径
     * */
    private static void uploadYeWuCustomsDeclaration(String customsDeclarationNumber, CusResult cusResult, Sftp sftp, WebDriver webDriver, String uploadPath) {
        // 获取报关单数据
        Map<String, Object> formHead = SWGDDatabaseUtil.getFormHead(customsDeclarationNumber);
        if (formHead == null) {
            throw new InfoException("报关单数据不存在");
        }

        // seqNo
        String seqNo = formHead.get("SEQ_NO") == null ? null : formHead.get("SEQ_NO").toString();
        if (StringUtil.isEmpty(seqNo)) {
            // 上传通讯回执
            uploadTongXunCustomsDeclaration(customsDeclarationNumber, null, sftp, webDriver, uploadPath);
        }

        // 再次获取seqNo
        formHead = SWGDDatabaseUtil.getFormHead(customsDeclarationNumber);
        if (formHead == null) {
            throw new InfoException("报关单数据不存在");
        }
        seqNo = formHead.get("SEQ_NO") == null ? null : formHead.get("SEQ_NO").toString();
        if (StringUtil.isEmpty(seqNo)) {
            throw new InfoException("SEQ_NO为空");
        }

        // 回执数据
        String preEntryId = formHead.get("PRE_ENTRY_ID") == null ? "" : formHead.get("PRE_ENTRY_ID").toString();
        if (preEntryId.equals(formHead.get("EDI_NO").toString())) {
            String declPort = formHead.get("DECL_PORT") == null ? "" : formHead.get("DECL_PORT").toString();
            String ieFlag = formHead.get("IE_FLAG") == null ? "" : formHead.get("IE_FLAG").toString();
            preEntryId = declPort + "0000" + ieFlag + "0000" + customsDeclarationNumber.substring(customsDeclarationNumber.length() - 5);
        }

        String cusResultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<DEC_RESULT>\n" +
                "\t<CUS_CIQ_NO>" + seqNo + "</CUS_CIQ_NO>\n" +
                "\t<ENTRY_ID>" + preEntryId + "</ENTRY_ID>\n" +
                "\t<NOTICE_DATE>" + DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss") + "</NOTICE_DATE>\n" +
                "\t<CHANNEL>" + cusResult.getCode() + "</CHANNEL>\n" +
                "\t<NOTE>" + cusResult.getNote() + "</NOTE>\n" +
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
        sftp.upload(uploadPath, "yeWuCusResult_" + customsDeclarationNumber, cusResultData);
        swgdRecvRun(webDriver);
    }

    /**
     * 点击RecvRun
     *
     * @param webDriver webDriver
     * */
    private static void swgdRecvRun(WebDriver webDriver) {
        try {
            webDriver.open("http://192.168.120.83:9909/console");
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(1) > td:nth-child(2) > input")).sendKey("admin");
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td:nth-child(2) > input")).sendKey("admin");
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(3) > td > button")).click();
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > div > div:nth-child(1) > table > tbody > tr > td > div > div:nth-child(2) > div > div:nth-child(2) > table > tbody > tr > td:nth-child(1) > img")).click();
            webDriver.findElement(Selector.byCssSelector("#gwt-uid-26 > span")).click();
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > div > div:nth-child(3) > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td > div > div > table > tbody > tr:nth-child(2) > td > div > div:nth-child(1) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(5) > td:nth-child(4) > table > tbody > tr > td:nth-child(3) > button")).click();
        } catch (Exception e) {
            throw new InfoException(e.getMessage());
        }
    }

}