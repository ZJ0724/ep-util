package com.easipass.epUtil.entity;

import com.easipass.epUtil.api.websocket.CusFileComparisonWebsocketApi;
import com.easipass.epUtil.component.Log;
import com.easipass.epUtil.component.oracle.SWGDOracle;
import com.easipass.epUtil.config.CusFileComparisonNodeConfig;
import com.easipass.epUtil.exception.CusFileException;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.exception.OracleException;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 报关单报文
 *
 * @author ZJ
 * */
public final class CusFile {

    /**
     * 表头
     * */
    private final Element decHead;

    /**
     * DecSign
     * */
    private final Element DecSign;

    /**
     * 报文集合
     * */
    private static final Map<String, CusFile> CUS_FILE_MAP = new LinkedHashMap<>();

    /**
     * 日志
     * */
    private static final Log LOG = Log.getLog();

    /**
     * 构造函数
     *
     * @param multipartFile 前端传过来的文件
     * */
    private CusFile(MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            Element rootElement = XmlUtil.getDocument_v2(inputStream).getRootElement();
            inputStream.close();

            this.decHead = rootElement.element("DecHead");
            if (this.decHead == null) {
                throw CusFileException.notCusFile();
            }

            this.DecSign = rootElement.element("DecSign");
            if (this.DecSign == null) {
                throw CusFileException.notCusFile();
            }
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getMessage());
        } catch (DocumentException e) {
            throw CusFileException.notCusFile();
        }
    }

    /**
     * 添加报文
     *
     * @param multipartFile 前端传过来的文件
     *
     * @return 唯一标识
     * */
    public static String addCusFile(MultipartFile multipartFile) {
        String id = new Date().getTime() + "";

        if (getCusFile(id) != null) {
            id = id + "1";
        }

        CUS_FILE_MAP.put(id, new CusFile(multipartFile));

        return id;
    }

    /**
     * 获取报文
     *
     * @return 通过id获取报文
     * */
    public static CusFile getCusFile(String id) {
        Set<String> keys = CUS_FILE_MAP.keySet();

        for (String key : keys) {
            if (key.equals(id)) {
                return CUS_FILE_MAP.get(id);
            }
        }

        return null;
    }

    /**
     * 比对
     *
     * @param ediNo 要比对的ediNo
     * @param cusFileComparisonWebsocketApi 报文比对websocket服务
     * */
    public void comparison(String ediNo, CusFileComparisonWebsocketApi cusFileComparisonWebsocketApi) {
        // 数据库连接
        SWGDOracle SWGDOracle = new SWGDOracle();

        try {
            // 比对表头信息
            ResultSet formHead = SWGDOracle.queryFormHead(ediNo);
            try {
                // 检查表头数据是不是存在
                if (!formHead.next()) {
                    throw new SQLException();
                }

                cusFileComparisonWebsocketApi.sendTitleMessage("[表头]");
                this.comparison(this.decHead, formHead, CusFileComparisonNodeConfig.FORM_HEAD, cusFileComparisonWebsocketApi);
            } catch (SQLException e) {
                cusFileComparisonWebsocketApi.sendComparisonMessage(ediNo + "，数据库表头数据不存在", false);
            }
        } catch (OracleException e) {
            cusFileComparisonWebsocketApi.sendComparisonMessage(e.getMessage(), false);
        }

        // 比对完成
        // 关闭数据库
        SWGDOracle.close();
        cusFileComparisonWebsocketApi.sendTitleMessage("[NONE]");
        cusFileComparisonWebsocketApi.close();
    }

    /**
     * 比对
     *
     * @param node 报文中要比对的节点
     * @param resultSet 数据库数据
     * @param config 比对节点配置
     * @param cusFileComparisonWebsocketApi 报文比对websocket服务
     * */
    private void comparison(Element node, ResultSet resultSet, Map<String, String> config, CusFileComparisonWebsocketApi cusFileComparisonWebsocketApi) {
        // keys
        Set<String> keys = config.keySet();

        for (String key : keys) {
            // 报文节点值
            String cusFileValue = node.element(key).getText();
            if (cusFileValue == null) {
                cusFileValue = "";
            }
            // 数据库节点值
            String dbValue;
            try {
                dbValue = resultSet.getString(config.get(key));
                if (dbValue == null) {
                    dbValue = "";
                }
            } catch (SQLException e) {
                throw ErrorException.getErrorException(e.getMessage() + config.get(key));
            }

            cusFileComparisonWebsocketApi.sendComparisonMessage(key, cusFileValue.equals(dbValue));
        }
    }

    /**
     * 删除报文
     *
     * @param id id
     * */
    public static void deleteCusFile(String id) {
        CUS_FILE_MAP.remove(id);
        LOG.info("已删除报文：" + id);
        LOG.info(CUS_FILE_MAP.toString());
    }

    /**
     * 获取ediNo
     * */
    public String getEdiNo() {
        return this.DecSign.element("ClientSeqNo").getText();
    }

}