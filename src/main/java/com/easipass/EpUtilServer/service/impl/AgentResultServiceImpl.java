package com.easipass.EpUtilServer.service.impl;

import com.easipass.EpUtilServer.config.ResourcePathConfig;
import com.easipass.EpUtilServer.entity.*;
import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.enumeration.ResponseEnum;
import com.easipass.EpUtilServer.exception.ErrorException;
import com.easipass.EpUtilServer.service.AgentResultService;
import com.easipass.EpUtilServer.service.BaseService;
import com.easipass.EpUtilServer.util.DateUtil;
import com.easipass.EpUtilServer.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AgentResultServiceImpl implements AgentResultService {

    @Resource
    private BaseService baseService;

    @Override
    public Response upload(String ediNo, ResultDTO resultDTO) {
        // 前置操作
        Response response = baseService.before(false, null);
        if (response.getFlag().equals(ResponseEnum.FALSE.getFlag())) {
            return response;
        }
        Sftp sftp = (Sftp) response.getData();

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
            return Response.returnFalse("", "KSDDB数据库连接失败");
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

        // 后置操作
        return baseService.after(document, "agentResult-" + ediNo + "-" + DateUtil.getTime(), false, sftp, null);
    }

}
