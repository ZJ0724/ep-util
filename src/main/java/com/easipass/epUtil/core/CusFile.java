package com.easipass.epUtil.core;

import com.easipass.epUtil.api.websocket.CusFileComparisonWebsocketApi;
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
     * 集装箱
     * */
    private final List<Element> DecContainers = new ArrayList<>();

    /**
     * 随附单证
     * */
    private final List<Element> LicenseDocu = new ArrayList<>();

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
        List<?> DecListList = DecLists.elements("DecList");
        for (Object element : DecListList) {
            this.DecList.add((Element) element);
        }

        // 集装箱
        Element DecContainers = rootElement.element("DecContainers");

        if (DecContainers != null) {
            List<?> ContainerList = DecContainers.elements("Container");

            for (Object element : ContainerList) {
                this.DecContainers.add((Element) element);
            }
        }

        // 随附单证
        Element DecLicenseDocus = rootElement.element("DecLicenseDocus");

        if (DecLicenseDocus != null) {
            List<?> LicenseDocu = DecLicenseDocus.elements("LicenseDocu");

            for (Object element : LicenseDocu) {
                this.LicenseDocu.add((Element) element);
            }
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
        ResultSet dbFormHead = SWGDOracle.queryFormHead(this.ediNo);
        String[] formHeadStrings = comparisonBefore(null, this.decHead, ComparisonNodeMapping.FORM_HEAD_MAPPING, ComparisonNodeMapping.FORM_HEAD_MAPPING.keySet(), dbFormHead, "表头", cusFileComparisonWebsocketApi);

        if (formHeadStrings != null) {
            // IEDate、DespDate、CmplDschrgDt特殊处理
            if (formHeadStrings[0].equals("IEDate") || formHeadStrings[0].equals("DespDate") || formHeadStrings[0].equals("CmplDschrgDt")) {
                formHeadStrings[3] = DateUtil.formatDateYYYYMMdd(formHeadStrings[3]);
            }

            // DeclareName特殊处理
            if ("DeclareName".equals(formHeadStrings[0])) {
                // IE_TYPE
                String ieType = getDbValue(dbFormHead, "IE_TYPE");

                // 如果IE_TYPE为0，这DeclareName为null
                if ("0".equals(ieType)) {
                    formHeadStrings[3] = "";
                }
            }
        }

        // 比对表体数据
        int size = this.DecList.size();
        Set<String> ListKeys = ComparisonNodeMapping.FORM_LIST_MAPPING.keySet();

        for (int i = 0; i < size; i++) {
            String[] strings = comparisonBefore(i, this.DecList.get(i), ComparisonNodeMapping.FORM_LIST_MAPPING, ListKeys, SWGDOracle.queryFormList(ediNo, i + ""), "表体", cusFileComparisonWebsocketApi);

            if (strings != null) {
                // ProdValidDt特殊处理
                if (strings[0].equals("ProdValidDt")) {
                    strings[4] = DateUtil.formatDateYYYYMMdd(strings[4]);
                }

                // 特殊处理GNo
                if (strings[0].equals("GNo")) {
                    strings[4] = (Integer.parseInt(strings[4]) + 1) + "";
                }
            }

            comparison(strings, cusFileComparisonWebsocketApi);
        }

        // 比对集装箱信息
        int containerSize = this.DecContainers.size();

        if (containerSize != 0) {
            Set<String> containersKeys = ComparisonNodeMapping.FORM_CONTAINER.keySet();

            for (int i = 0; i < containerSize; i ++) {
                String[] strings = comparisonBefore(i, this.DecContainers.get(i), ComparisonNodeMapping.FORM_CONTAINER, containersKeys, SWGDOracle.queryFormContainer(ediNo, i + ""), "集装箱", cusFileComparisonWebsocketApi);

                comparison(strings, cusFileComparisonWebsocketApi);
            }
        } else {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getTitleType("[无集装箱]"));
        }

        // 比对随附单证
        int licenseDocuSize = this.LicenseDocu.size();

        if (licenseDocuSize != 0) {
            Set<String> certificateKeys = ComparisonNodeMapping.FORM_CERTIFICATE.keySet();

            for (int i = 0; i < licenseDocuSize; i++) {
                String[] strings = comparisonBefore(i, this.LicenseDocu.get(i), ComparisonNodeMapping.FORM_CERTIFICATE, certificateKeys, SWGDOracle.queryFormCertificate(ediNo, i + ""), "随附单证", cusFileComparisonWebsocketApi);

                comparison(strings, cusFileComparisonWebsocketApi);
            }
        } else {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getTitleType("[无随附单证]"));
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
     * @param strings 节点数据
     * @param cusFileComparisonWebsocketApi 报文比对websocket服务
     * */
    private static void comparison(String[] strings, CusFileComparisonWebsocketApi cusFileComparisonWebsocketApi) {
        if (strings == null) {
            return;
        }

        String key = strings[0];
        String nodeValue = strings[1];
        String value = strings[2];
        String dbValue = strings[3];

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

    /**
     * 获取节点内容
     *
     * @param element 父节点
     * @param elementName 子节点名
     *
     * @return 节点值
     * */
    private static String getNodeValue(Element element, String elementName) {
        Element cElement = element.element(elementName);

        if (cElement == null) {
            return "";
        }

        return cElement.getText();
    }

    /**
     * 获取dbValue
     *
     * @param resultSet 数据库数据
     * @param value 字段名
     * */
    private static String getDbValue(ResultSet resultSet, String value) {
        if (value == null) {
            return "";
        }

        String result;

        try {
            result = resultSet.getString(value);
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage() + ": " + value);
        }

        return result;
    }

    /**
     * 获取key、nodeValue、value、dbValue
     *
     * @param index 序号
     * @param element 元素
     * @param map 映射
     * @param keys 映射keys
     * @param resultSet 数据库数据
     * @param message 信息
     * @param cusFileComparisonWebsocketApi 报文比对websocket服务
     * */
    private static String[] comparisonBefore(Integer index, Element element, Map<String, String> map, Set<String> keys, ResultSet resultSet, String message, CusFileComparisonWebsocketApi cusFileComparisonWebsocketApi) {
        if (resultSet == null) {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getErrorType("数据库" + message + "不存在"));
            return null;
        }

        String[] result = new String[4];

        // 遍历映射keys
        if (index == null) {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getTitleType("[" + message + "]"));
        } else {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVo.getTitleType("[" + message + " - " + (index + 1) + "]"));
        }
        for (String key : keys) {
            String key1 = ComparisonNodeMapping.getKey(key);

            result[0] = key;
            result[1] = getNodeValue(element, key1);
            result[2] = map.get(key);
            result[3] = getDbValue(resultSet, result[2]);
        }

        return result;
    }

}