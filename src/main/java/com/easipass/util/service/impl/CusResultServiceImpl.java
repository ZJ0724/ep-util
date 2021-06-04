package com.easipass.util.service.impl;

import com.easipass.util.entity.CusResult;
import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.service.ConfigService;
import com.easipass.util.service.CusResultService;
import com.easipass.util.util.ChromeDriverUtil;
import com.easipass.util.util.SWGDDatabaseUtil;
import com.easipass.util.util.SftpUtil;
import com.zj0724.common.component.ftp.Sftp;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.Base64Util;
import com.zj0724.common.util.DateUtil;
import com.zj0724.common.util.MapUtil;
import com.zj0724.common.util.StringUtil;
import com.zj0724.uiAuto.WebDriver;
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
        WebDriver webDriver = ChromeDriverUtil.getChromeDriver();

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

    @Override
    public void uploadAgentResult(String customsDeclarationNumber, CusResult cusResult) {
        // 校验
        if (StringUtil.isEmpty(customsDeclarationNumber)) {
            throw new InfoException("报关单号不能为空");
        }
        if (cusResult == null) {
            throw new InfoException("回执信息为空");
        }
        String code = cusResult.getCode();
        String note = cusResult.getNote();
        if (StringUtil.isEmpty(code)) {
            throw new InfoException("code不能为空");
        }
        if (StringUtil.isEmpty(note)) {
            throw new InfoException("note不能为空");
        }

        // 获取代理委托上传路径
        ConfigPO configPO = configService.getByCode(ConfigPO.Code.UPLOAD_AGENT_RESULT_SFTP_PATH);
        String agentUploadPath = null;
        if (configPO != null) {
            agentUploadPath = configPO.getData();
        }
        if (StringUtil.isEmpty(agentUploadPath)) {
            throw new InfoException("上传代理委托回执路径为空");
        }

        // 查询agent信息
        Map<String, Object> agentData = SWGDDatabaseUtil.queryOne("SELECT * FROM SWGD.T_SWGD_AGENT_LIST WHERE EDI_NO = '" + customsDeclarationNumber + "'");
        if (agentData == null) {
            throw new InfoException("未查询到数据：" + customsDeclarationNumber);
        }

        // 校验userName不能为空
        if (StringUtil.isEmpty(agentData.get("USER_NAME"))) {
            throw new InfoException("USER_NAME为空");
        }

        // 校验fileName不能为空
        String fileName = MapUtil.getValue(agentData, "FILE_NAME", String.class);
        if (StringUtil.isEmpty(fileName)) {
            throw new InfoException("FILE_NAME为空");
        }

        // 回执信息
        String resultData;
        if ("999".equals(code)) {
            resultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<ImportAgrResponse>\n" +
                    "\t<ResponseInfo>\n" +
                    "\t\t<ResponseMessage>" + note + "</ResponseMessage>\n" +
                    "\t</ResponseInfo>\n" +
                    "</ImportAgrResponse>";
        } else {
            // 获取代理委托编号
            String ConsignNo = "000" + customsDeclarationNumber;
            resultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<ImportAgrResponse>\n" +
                    "\t<ResponseInfo>\n" +
                    "\t\t<ResponseCode>" + code + "</ResponseCode>\n" +
                    "\t\t<ResponseMessage>" + note + "</ResponseMessage>\n" +
                    "\t</ResponseInfo>\n" +
                    "\t<ConsignNo>" + ConsignNo + "</ConsignNo>\n" +
                    "</ImportAgrResponse>";
        }

        // 上传
        Sftp uploadCusResultSftp = SftpUtil.getUploadCusResultSftp();
        try {
            uploadCusResultSftp.upload(agentUploadPath, fileName, resultData);
        } finally {
            uploadCusResultSftp.close();
        }

        // run
        WebDriver webDriver = ChromeDriverUtil.getChromeDriver();
        try {
            ChromeDriverUtil.agentRun(webDriver);
        } finally {
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
        ChromeDriverUtil.swgdRecvRun(webDriver);
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
            seqNo = "seqNo00000000" + customsDeclarationNumber.substring(customsDeclarationNumber.length() - 5);
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
        ChromeDriverUtil.swgdRecvRun(webDriver);

        // N回执清空SEQ_NO, CUS_CIQ_NO
        if ("N".equals(cusResult.getCode())) {
            SWGDDatabaseUtil.execute("UPDATE SWGD.T_SWGD_FORM_HEAD SET SEQ_NO = NULL, CUS_CIQ_NO = NULL WHERE EDI_NO = '" + formHead.get("EDI_NO") + "'");
        }
    }

}