package com.easipass.util.core.util;

import com.easipass.util.core.Resource;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.InfoException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import java.io.*;

public class XmlUtil {

    /**
     * 获取document by InputStream
     *
     * @param InputStream InputStream
     *
     * @return Document
     */
    public static Document getDocument(InputStream InputStream) {
        try {
            SAXReader saxReader = new SAXReader();
            return saxReader.read(InputStream);
        } catch (DocumentException e) {
            throw new ErrorException(e.getMessage());
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

    /**
     * get document by InputStream
     *
     * @param resource 资源
     *
     * @return 文档对象
     */
    public static Document getDocument(Resource resource) {
        SAXReader saxReader = new SAXReader();
        try {
            InputStream inputStream = new FileInputStream(new File(resource.getPath()));
            Document document = saxReader.read(inputStream);
            inputStream.close();
            return document;
        } catch (IOException | DocumentException e) {
            throw new InfoException(e.getMessage());
        }
    }

    /**
     * get document by filePath
     *
     * @param filePath 文件路径
     *
     * @return 文档对象
     * */
    public static Document getDocument(String filePath) {
        InputStream inputStream = IOUtil.getInputStream(filePath);
        try {
            return getDocument(inputStream);
        } finally {
            IOUtil.close(inputStream);
        }
    }

    /**
     * 通过字符串获取data
     *
     * @param data 数据
     *
     * @return 文档对象
     * */
    public static Document getDocumentByData(String data) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        try {
            return getDocument(inputStream);
        } finally {
            IOUtil.close(inputStream);
        }
    }

}