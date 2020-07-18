package com.easipass.epUtil.entity;

import com.easipass.epUtil.api.websocket.CusFileComparisonWebsocketApi;
import com.easipass.epUtil.entity.oracle.SWGDOracle;
import com.easipass.epUtil.entity.cusFile.ComparisonNodeMapping;
import com.easipass.epUtil.entity.VO.CusFileComparisonMessageVO;
import com.easipass.epUtil.exception.CusFileException;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.exception.OracleException;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.StringUtil;
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
public final class CusFile {

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
     * 申请单证
     * */
    private final List<Element> DecRequestCert = new ArrayList<>();

    /**
     * 企业资质
     * */
    private final List<Element> DecCopLimit = new ArrayList<>();

    /**
     * 企业承诺
     * */
    private final List<Element> DecCopPromise = new ArrayList<>();

    /**
     * 其他包装
     * */
    private final List<Element> DecOtherPack = new ArrayList<>();

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
            throw new ErrorException(e.getMessage());
        } catch (DocumentException e) {
            throw new CusFileException("不是正确的报文");
        }

        this.decHead = rootElement.element("DecHead");
        if (this.decHead == null) {
            throw new CusFileException("不是正确的报文");
        }

        Element DecSign = rootElement.element("DecSign");
        if (DecSign == null) {
            throw new CusFileException("不是正确的报文");
        }
        Element ClientSeqNo = DecSign.element("ClientSeqNo");
        if (ClientSeqNo == null) {
            throw new CusFileException("不是正确的报文");
        }
        String ediNo = ClientSeqNo.getText();
        if ("".equals(ediNo)) {
            throw new CusFileException("不是正确的报文");
        }
        this.ediNo = ediNo;

        Element DecLists = rootElement.element("DecLists");
        if (DecLists == null) {
            throw new CusFileException("不是正确的报文");
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

        // 申请单证
        Element DecRequestCerts = rootElement.element("DecRequestCerts");

        if (DecRequestCerts != null) {
            List<?> DecRequestCert = DecRequestCerts.elements("DecRequestCert");

            for (Object element : DecRequestCert) {
                this.DecRequestCert.add((Element) element);
            }
        }

        // 企业资质
        Element DecCopLimits = rootElement.element("DecCopLimits");

        if (DecCopLimits != null) {
            List<?> DecCopLimit = DecCopLimits.elements("DecCopLimit");

            for (Object element : DecCopLimit) {
                this.DecCopLimit.add((Element) element);
            }
        }

        // 企业承诺
        Element DecCopPromises = rootElement.element("DecCopPromises");

        if (DecCopPromises != null) {
            List<?> DecCopPromise = DecCopPromises.elements("DecCopPromise");

            for (Object element : DecCopPromise) {
                this.DecCopPromise.add((Element) element);
            }
        }

        // 其他包装
        Element DecOtherPacks = rootElement.element("DecOtherPacks");

        if (DecOtherPacks != null) {
            List<?> DecOtherPack = DecOtherPacks.elements("DecOtherPack");

            for (Object element : DecOtherPack) {
                this.DecOtherPack.add((Element) element);
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
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getErrorType(e.getMessage()));
            cusFileComparisonWebsocketApi.close();
            return;
        }

        // 比对表头
        ResultSet dbFormHead = SWGDOracle.queryFormHead(this.ediNo);
        Set<String> formHeadKeys = ComparisonNodeMapping.FORM_HEAD_MAPPING.keySet();

        cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[表头]"));
        for (String key : formHeadKeys) {
            String[] formHeadStrings = comparisonBefore(this.decHead, ComparisonNodeMapping.FORM_HEAD_MAPPING, key, dbFormHead, "表头", cusFileComparisonWebsocketApi);

            if (formHeadStrings != null) {
                // IEDate、DespDate、CmplDschrgDt特殊处理
                if (formHeadStrings[4].equals("IEDate") || formHeadStrings[4].equals("DespDate") || formHeadStrings[4].equals("CmplDschrgDt")) {
                    formHeadStrings[3] = DateUtil.formatDateYYYYMMdd(formHeadStrings[3]);
                }

                // DeclareName特殊处理
                if ("DeclareName".equals(formHeadStrings[4])) {
                    // IE_TYPE
                    String ieType = getDbValue(dbFormHead, "IE_TYPE");

                    // 如果IE_TYPE为0，则DeclareName可以为null
                    if ("0".equals(ieType)) {
                        if ("".equals(formHeadStrings[1])) {
                            formHeadStrings[3] = "";
                        }
                    }
                }
            } else {
                break;
            }

            comparison(formHeadStrings, cusFileComparisonWebsocketApi);
        }

        // 比对表体数据
        int size = this.DecList.size();
        Set<String> ListKeys = ComparisonNodeMapping.FORM_LIST_MAPPING.keySet();

        for (int i = 0; i < size; i++) {
            Element element = this.DecList.get(i);
            ResultSet resultSet = SWGDOracle.queryFormList(ediNo, i + "");
            // codeTs
            String codeTs = "";

            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[表体 - " + (i + 1) + "]"));
            for (String key : ListKeys) {
                String[] strings = comparisonBefore(element, ComparisonNodeMapping.FORM_LIST_MAPPING, key, resultSet, "表体", cusFileComparisonWebsocketApi);

                if (strings != null) {
                    // ProdValidDt特殊处理
                    if (strings[4].equals("ProdValidDt")) {
                        strings[3] = DateUtil.formatDateYYYYMMdd(strings[3]);
                    }

                    // 特殊处理GNo
                    if (strings[4].equals("GNo")) {
                        strings[3] = (Integer.parseInt(strings[3]) + 1) + "";
                    }

                    // 特殊处理CodeTS
                    if ("CODE_T".equals(strings[2])) {
                        codeTs = StringUtil.append(codeTs, strings[3]);
                        continue;
                    }
                    if ("CODE_S".equals(strings[2])) {
                        codeTs = StringUtil.append(codeTs, strings[3]);
                        strings[3] = codeTs;
                    }
                } else {
                    break;
                }

                comparison(strings, cusFileComparisonWebsocketApi);
            }
        }

        // 比对集装箱信息
        int containerSize = this.DecContainers.size();

        if (containerSize != 0) {
            Set<String> containersKeys = ComparisonNodeMapping.FORM_CONTAINER.keySet();

            for (int i = 0; i < containerSize; i ++) {
                Element element = this.DecContainers.get(i);
                ResultSet resultSet = SWGDOracle.queryFormContainer(ediNo, i + "");

                cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[集装箱 - " + (i + 1) + "]"));
                for (String key : containersKeys) {
                    String[] strings = comparisonBefore(element, ComparisonNodeMapping.FORM_CONTAINER, key, resultSet, "集装箱", cusFileComparisonWebsocketApi);

                    if (strings == null) {
                        break;
                    }

                    comparison(strings, cusFileComparisonWebsocketApi);
                }
            }
        } else {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[无集装箱]"));
        }

        // 比对随附单证
        int licenseDocuSize = this.LicenseDocu.size();

        if (licenseDocuSize != 0) {
            Set<String> certificateKeys = ComparisonNodeMapping.FORM_CERTIFICATE.keySet();

            for (int i = 0; i < licenseDocuSize; i++) {
                Element element = this.LicenseDocu.get(i);
                ResultSet resultSet = SWGDOracle.queryFormCertificate(ediNo, i + "");

                cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[随附单证 - " + (i + 1) + "]"));
                for (String key : certificateKeys) {
                    String[] strings = comparisonBefore(element, ComparisonNodeMapping.FORM_CERTIFICATE, key, resultSet, "随附单证", cusFileComparisonWebsocketApi);

                    if (strings == null) {
                        break;
                    }

                    comparison(strings, cusFileComparisonWebsocketApi);
                }
            }
        } else {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[无随附单证]"));
        }

        // 比对申请单证
        String decRequestCertMessage = "申请单证";
        int DecRequestCertSize = this.DecRequestCert.size();

        if (DecRequestCertSize != 0) {
            Set<String> keys = ComparisonNodeMapping.DEC_REQUEST_CERT_MAPPING.keySet();

            for (int i = 0; i < DecRequestCertSize; i++) {
                Element element = this.DecRequestCert.get(i);
                ResultSet resultSet = SWGDOracle.queryDecRequestCert(ediNo, (i + 1) + "");

                cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[" + decRequestCertMessage + " - " + (i + 1) + "]"));
                for (String key : keys) {
                    String[] strings = comparisonBefore(element, ComparisonNodeMapping.DEC_REQUEST_CERT_MAPPING, key, resultSet, decRequestCertMessage, cusFileComparisonWebsocketApi);

                    if (strings == null) {
                        break;
                    }

                    comparison(strings, cusFileComparisonWebsocketApi);
                }
            }
        } else {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[无" + decRequestCertMessage + "]"));
        }

        // 比对企业资质
        String decCopLimitMessage = "企业资质";
        int decCopLimitSize = this.DecCopLimit.size();

        if (decCopLimitSize != 0) {
            Set<String> keys = ComparisonNodeMapping.DEC_COP_LIMIT_MAPPING.keySet();

            for (int i = 0; i < decCopLimitSize; i++) {
                Element element = this.DecCopLimit.get(i);
                ResultSet resultSet = SWGDOracle.queryDecCopLimit(ediNo, (i + 1) + "");

                cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[" + decCopLimitMessage + " - " + (i + 1) + "]"));
                for (String key : keys) {
                    String[] strings = comparisonBefore(element, ComparisonNodeMapping.DEC_COP_LIMIT_MAPPING, key, resultSet, decRequestCertMessage, cusFileComparisonWebsocketApi);

                    if (strings == null) {
                        break;
                    }

                    comparison(strings, cusFileComparisonWebsocketApi);
                }
            }
        } else {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[无" + decCopLimitMessage + "]"));
        }

        // 比对企业承诺
        String decCopPromisesMessage = "企业承诺";
        int decCopPromisesSize = this.DecCopPromise.size();

        if (decCopPromisesSize != 0) {
            Set<String> keys = ComparisonNodeMapping.DEC_COP_PROMISE_MAPPING.keySet();

            for (int i = 0; i < decCopPromisesSize; i++) {
                Element element = this.DecCopPromise.get(i);
                ResultSet resultSet = SWGDOracle.queryDecCopPromise(ediNo, (i + 1) + "");

                cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[" + decCopPromisesMessage + " - " + (i + 1) + "]"));
                for (String key : keys) {
                    String[] strings = comparisonBefore(element, ComparisonNodeMapping.DEC_COP_PROMISE_MAPPING, key, resultSet, decRequestCertMessage, cusFileComparisonWebsocketApi);

                    if (strings == null) {
                        break;
                    }

                    comparison(strings, cusFileComparisonWebsocketApi);
                }
            }
        } else {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[无" + decCopPromisesMessage + "]"));
        }

        // 使用人信息暂时不进行比对
        cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[使用人信息暂时不进行比对]"));

        // 标记号码附件暂时不进行比对
        cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[标记号码附件暂时不进行比对]"));

        // 比对其他包装
        String decOtherPackMessage = "其他包装";
        int decOtherPackSize = this.DecOtherPack.size();

        if (decOtherPackSize != 0) {
            Set<String> keys = ComparisonNodeMapping.DEC_OTHER_PACK_MAPPING.keySet();

            for (int i = 0; i < decOtherPackSize; i++) {
                Element element = this.DecOtherPack.get(i);
                ResultSet resultSet = SWGDOracle.queryDecOtherPack(ediNo, (i + 1) + "");

                cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[" + decOtherPackMessage + " - " + (i + 1) + "]"));
                for (String key : keys) {
                    String[] strings = comparisonBefore(element, ComparisonNodeMapping.DEC_OTHER_PACK_MAPPING, key, resultSet, decOtherPackMessage, cusFileComparisonWebsocketApi);

                    if (strings == null) {
                        break;
                    }

                    comparison(strings, cusFileComparisonWebsocketApi);
                }
            }
        } else {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[无" + decOtherPackMessage + "]"));
        }

        // 比对完成
        cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getTitleType("[NONE]"));
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
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getComparisonNullType(key));
            return;
        }

        if (nodeValue == null) {
            nodeValue = "";
        }
        if (dbValue == null) {
            dbValue = "";
        }

        if (nodeValue.equals(dbValue)) {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getComparisonTrueType(key));
        } else {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getComparisonFalseType(key));
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
     * 获取key、nodeValue、value、dbValue, key1
     *
     * @param element 元素
     * @param map 映射
     * @param key 映射key
     * @param resultSet 数据库数据
     * @param message 信息
     * @param cusFileComparisonWebsocketApi 报文比对websocket服务
     * */
    private static String[] comparisonBefore(Element element, Map<String, String> map, String key, ResultSet resultSet, String message, CusFileComparisonWebsocketApi cusFileComparisonWebsocketApi) {
        if (resultSet == null) {
            cusFileComparisonWebsocketApi.sendMessage(CusFileComparisonMessageVO.getErrorType("数据库" + message + "不存在"));
            return null;
        }

        String[] result = new String[5];
        String key1 = ComparisonNodeMapping.getKey(key);

        result[0] = key;
        result[1] = getNodeValue(element, key1);
        result[2] = map.get(key);
        result[3] = getDbValue(resultSet, result[2]);
        result[4] = key1;

        return result;
    }

}