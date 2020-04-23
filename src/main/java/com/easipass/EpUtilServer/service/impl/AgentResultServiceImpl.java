package com.easipass.EpUtilServer.service.impl;

import com.easipass.EpUtilServer.annotation.UploadResultAnnotation;
import com.easipass.EpUtilServer.config.ResourcePathConfig;
import com.easipass.EpUtilServer.entity.*;
import com.easipass.EpUtilServer.entity.ResultDTO;
import com.easipass.EpUtilServer.exception.ErrorException;
import com.easipass.EpUtilServer.service.AgentResultService;
import com.easipass.EpUtilServer.service.BaseService;
import com.easipass.EpUtilServer.util.DateUtil;
import com.easipass.EpUtilServer.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AgentResultServiceImpl implements AgentResultService {

    @Override
    @UploadResultAnnotation
    public Response upload(String ediNo, ResultDTO resultDTO) {
        // 获取回执原节点
        Document document = XmlUtil.getDocument(AgentResultServiceImpl.class.getResourceAsStream(ResourcePathConfig.AGENT_RESULT_PATH));

        // 获取根节点
        Element rootElement = document.getRootElement();

        // 替换数据
        Element ResponseInfo = rootElement.element("ResponseInfo");
        ResponseInfo.element("ResponseCode").setText(resultDTO.getChannel());
        ResponseInfo.element("ResponseNotes").setText(resultDTO.getNote());
        rootElement.element("ConsignNo").setText(ResultDTO.getAgentSeqNo(ediNo));
        rootElement.element("DecEntryID").setText(ediNo);
        // 查询AGENT_CODE
        Oracle oracle = Oracle.getKSDDBOracle();
        if (!oracle.connect()) {
            return Response.returnFalse("KSDDB数据库连接失败");
        }
        ResultSet resultSet = oracle.query("SELECT AGENT_CODE FROM KSDDB.T_KSD_FORM_HEAD WHERE EDI_NO = ?", new Object[]{ediNo});
        String agentCode;
        try {
            resultSet.next();
            agentCode = resultSet.getString("AGENT_CODE");
        } catch (SQLException e) {
            throw ErrorException.getErrorException("sql错误");
        }
        ResponseInfo.element("CopCusCode").setText(agentCode);
        oracle.close();

        BaseService.uploadResult(document, "agentResult-" + ediNo + "-" + DateUtil.getTime());

        return Response.returnTrue(null);
    }

}
