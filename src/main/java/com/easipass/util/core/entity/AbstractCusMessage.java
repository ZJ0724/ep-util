package com.easipass.util.core.entity;

import com.easipass.util.core.exception.CusMessageException;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

/**
 * 报文抽象类
 *
 * @author ZJ
 * */
public abstract class AbstractCusMessage {

    /**
     * document
     * */
    public final Document document;

    /**
     * 根节点
     * */
    public final Element rootElement;

    /**
     * constructor
     *
     * @param multipartFile multipartFile
     * */
    public AbstractCusMessage(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new CusMessageException("报文为空");
        }

        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        try {
            this.document = XmlUtil.getDocument_v2(inputStream);
        } catch (DocumentException e) {
            throw new CusMessageException("报文解析错误");
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.rootElement = this.document.getRootElement();
    }

    /**
     * 获取根节点
     *
     * @return rootElement
     * */
    public final Element getRootElement() {
        return this.rootElement;
    }

}