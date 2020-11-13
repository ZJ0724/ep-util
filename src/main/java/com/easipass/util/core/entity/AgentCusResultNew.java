package com.easipass.util.core.entity;

import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.config.ResourcesConfig;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.exception.InfoException;
import com.easipass.util.core.util.Base64Util;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.StringUtil;
import com.easipass.util.core.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import java.util.List;

/**
 * 新代理委托回执
 *
 * @author ZJ
 * */
public final class AgentCusResultNew implements CusResult {

    /**
     * ediNo
     * */
    private final String ediNo;

    /**
     * 状态
     * */
    private final String channel;

    /**
     * 信息
     * */
    private final String message;

    /**
     * 代理委托协议号
     * */
    private final String consignNo;

    /**
     * 构造函数
     *
     * @param ediNo ediNo
     * @param channel 状态
     * @param message 信息
     * */
    public AgentCusResultNew(String ediNo, String channel, String message) {
        if (StringUtil.isEmpty(ediNo)) {
            throw new InfoException("ediNo不能为空");
        }
        if (StringUtil.isEmpty(channel)) {
            throw new InfoException("channel不能为空");
        }
        if (StringUtil.isEmpty(message)) {
            throw new InfoException("message不能为空");
        }

        this.ediNo = ediNo;
        this.channel = channel;
        this.message = message;

        this.consignNo = "consignNo-" + ediNo;
    }

    @Override
    public String getData() {
        Document document = XmlUtil.getDocument(ResourcesConfig.AGENT_CUS_RESULT_NEW);
        Element rootElement = document.getRootElement();
        Element TransInfo = rootElement.element("TransInfo");
        Element CreatTime = TransInfo.element("CreatTime");
        Element Data = rootElement.element("Data");
        Element AddInfo = rootElement.element("AddInfo");
        Element FileName = AddInfo.element("FileName");

        // 设置创建时间
        CreatTime.setText(DateUtil.getDate());
        // 设置文件名
        FileName.setText(this.getFileName());

        // 解密后的data转成document
        Document dataDocument = XmlUtil.getDocumentByData(Base64Util.decode(Data.getText()));
        Element rootDataDocument = dataDocument.getRootElement();
        Element ResponseInfo = rootDataDocument.element("ResponseInfo");
        Element ResponseCode = ResponseInfo.element("ResponseCode");
        Element ResponseMessage = ResponseInfo.element("ResponseMessage");
        Element ConsignNo = rootDataDocument.element("ConsignNo");

        // 设置状态
        ResponseCode.setText(this.channel);
        // 设置信息
        ResponseMessage.setText(this.message);
        // 设置代理委托协议号
        ConsignNo.setText(this.consignNo);

        // 加密替换data
        Data.setText(Base64Util.encode(dataDocument.asXML()));

        return document.asXML();
    }

    @Override
    public String getFileName() {
        List<JSONObject> jsonObjects = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_SWGD_AGENT_LIST WHERE EDI_NO = '" + this.ediNo + "'");

        if (jsonObjects.size() == 0) {
            throw new InfoException("ediNo：" + this.ediNo + "，不存在代理委托信息");
        }
        if (jsonObjects.size() != 1) {
            throw new InfoException("ediNo：" + this.ediNo + "，存在多条代理委托信息");
        }

        JSONObject jsonObject = jsonObjects.get(0);
        String fileName = jsonObject.getString("FILE_NAME");

        if (StringUtil.isEmpty(fileName)) {
            fileName = "agentCusResultNew-" + this.ediNo + "-" + DateUtil.getTime();
            // 设置文件名
            SWGDDatabase.update("UPDATE " + SWGDDatabase.SWGD + ".T_SWGD_AGENT_LIST SET FILE_NAME = '" + fileName + "' WHERE EDI_NO = '" + this.ediNo + "'");
        }

        return fileName;
    }

}