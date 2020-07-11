package com.easipass.epUtil.core;

import com.easipass.epUtil.api.websocket.CusFileComparisonWebsocketApi;
import com.easipass.epUtil.component.Log;
import com.easipass.epUtil.component.oracle.SWGDOracle;
import com.easipass.epUtil.core.cusFile.ComparisonNodeMapping;
import com.easipass.epUtil.entity.vo.CusFileComparisonMessageVo;
import com.easipass.epUtil.exception.CusFileException;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.exception.OracleException;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 报关单报文
 *
 * @author ZJ
 * */
public class CusFile {

    /**
     * 报文集合
     * */
    private static final Map<String, CusFile> CUS_FILE_MAP = new LinkedHashMap<>();

    /**
     * 表头
     * */
    private final Element decHead;

    /**
     * 表体
     * */
    private final List<Element> DecList = new ArrayList<>();

    /**
     * ediNo
     * */
    private final String ediNo;

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
        Element rootElement;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            rootElement = XmlUtil.getDocument_v2(inputStream).getRootElement();
            inputStream.close();
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getMessage());
        } catch (DocumentException e) {
            throw CusFileException.notCusFile();
        }

        this.decHead = rootElement.element("DecHead");
        if (this.decHead == null) {
            throw CusFileException.notCusFile();
        }

        Element DecSign = rootElement.element("DecSign");
        if (DecSign == null) {
            throw CusFileException.notCusFile();
        }
        Element ClientSeqNo = DecSign.element("ClientSeqNo");
        if (ClientSeqNo == null) {
            throw CusFileException.notCusFile();
        }
        String ediNo = ClientSeqNo.getText();
        if ("".equals(ediNo)) {
            throw CusFileException.notCusFile();
        }
        this.ediNo = ediNo;

        Element DecLists = rootElement.element("DecLists");
        if (DecLists == null) {
            throw CusFileException.notCusFile();
        }
        this.DecList.addAll(DecLists.elements("DecList"));
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
     * @param cusFileComparisonWebsocketApi 报文比对websocket服务
     * */
    public void comparison(CusFileComparisonWebsocketApi cusFileComparisonWebsocketApi) {
        // 数据库
        SWGDOracle SWGDOracle = new SWGDOracle();

        // 检查数据库连接
        try {
            SWGDOracle.connect();
        } catch (OracleException e) {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getErrorType(e.getMessage()));
            cusFileComparisonWebsocketApi.close();
            return;
        }

        // 比对表头
        ResultSet formHead = SWGDOracle.queryFormHead(this.ediNo);

        cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getTitleType("[表头]"));
        if (formHead != null) {
            // 遍历表头映射
            Set<String> keys = ComparisonNodeMapping.FORM_HEAD_MAPPING.keySet();
            for (String key : keys) {
                String key1 = ComparisonNodeMapping.getKey(key);
                String nodeValue = this.decHead.element(key1).getText();
                String value = ComparisonNodeMapping.FORM_HEAD_MAPPING.get(key);
                String dbValue = null;
                if (value != null) {
                    try {
                        dbValue = formHead.getString(value);
                    } catch (SQLException e) {
                        throw ErrorException.getErrorException(e.getMessage() + value);
                    }
                }

                // IEDate特殊处理
                if (key1.equals("IEDate")) {
                    dbValue = DateUtil.formatDate(dbValue, "yyyy-MM-dd", "yyyyMMdd");
                }

                // DespDate特殊处理
                if (key1.equals("DespDate")) {
                    dbValue = DateUtil.formatDate(dbValue, "yyyy-MM-dd", "yyyyMMdd");
                }

                this.comparison(key, nodeValue, value, dbValue, cusFileComparisonWebsocketApi);
            }
        } else {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getErrorType(this.ediNo + "，数据库表头数据不存在"));
        }

        // 比对表体数据
        int size = this.DecList.size();
        for (int i = 0; i < size; i++) {
            // DecList
            Element DecList = this.DecList.get(i);
            // GNo
            String GNo = DecList.element("GNo").getText();

            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getTitleType("[表体 - " + (i + 1) + "]"));
            // GNo为null
            if (GNo == null || "".equals(GNo)) {
                cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getComparisonFalseType("GNo"));
                continue;
            }

            // 表体数据
            ResultSet formList = SWGDOracle.queryFormList(this.ediNo, (Integer.parseInt(GNo) - 1) + "");
            // 表体映射keys
            Set<String> keys = ComparisonNodeMapping.FORM_LIST_MAPPING.keySet();

            // 遍历表体映射
            for (String key : keys) {
                String key1 = ComparisonNodeMapping.getKey(key);
                String nodeValue = DecList.element(key1).getText();
                String value = ComparisonNodeMapping.FORM_LIST_MAPPING.get(key);
                StringBuilder dbValue = new StringBuilder();
                try {
                    if (value != null) {
                        // 特殊处理CodeTS
                        if (key1.equals("CodeTS")) {
                            String[] fields = value.split(",");
                            for (String field : fields) {
                                String fieldValue = formList.getString(field);
                                if (fieldValue != null) {
                                    dbValue.append(fieldValue);
                                }
                            }
                        } else {
                            dbValue = new StringBuilder(formList.getString(value));
                        }
                    }
                } catch (SQLException e) {
                    throw ErrorException.getErrorException(e.getMessage() + value);
                }

                this.comparison(key, nodeValue, value, dbValue.toString(), cusFileComparisonWebsocketApi);
            }
        }

        // 比对完成
        cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getTitleType("[NONE]"));
        // 关闭数据库
        SWGDOracle.close();
        // 关闭websocket连接
        cusFileComparisonWebsocketApi.close();
    }

    /**
     * 比对
     *
     * @param key 映射key值
     * @param nodeValue 报文节点值
     * @param value 映射value值
     * @param dbValue 数据库值
     * @param cusFileComparisonWebsocketApi 报文比对websocket服务
     * */
    private void comparison(String key, String nodeValue, String value, String dbValue, CusFileComparisonWebsocketApi cusFileComparisonWebsocketApi) {
        // 如果value是null的话，不进行比对
        if (value == null) {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getComparisonNullType(key));
            return;
        }

        if (nodeValue == null) {
            nodeValue = "";
        }
        if (dbValue == null) {
            dbValue = "";
        }

        if (nodeValue.equals(dbValue)) {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getComparisonTrueType(key));
        } else {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getComparisonFalseType(key));
        }
    }

}