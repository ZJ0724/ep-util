package com.easipass.epUtil.util;

import com.easipass.epUtil.exception.ErrorException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import java.io.*;

public class XmlUtil {

    /**
     * 获取document by InputStream
     */
    public static Document getDocument(InputStream InputStream) {
        try {
            SAXReader saxReader = new SAXReader();
            return saxReader.read(InputStream);
        } catch (DocumentException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }
    }

    /**
     * get document by InputStream
     *
     * @param InputStream 输入流
     *
     * @return 文档对象
     *
     * @exception DocumentException 解析错误
     */
    public static Document getDocument_v2(InputStream InputStream) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        return saxReader.read(InputStream);
    }

}