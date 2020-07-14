package com.easipass.epUtil.entity;

import com.easipass.epUtil.exception.ErrorException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 资源
 *
 * @author ZJ
 * */
public abstract class Resource {

    /**
     * 资源根路径
     * */
    private static final String ROOT_PATH = "/resources/";

    /**
     * 资源路径
     * */
    private final String path;

    /**
     * 资源流
     * */
    private InputStream inputStream;

    /**
     * 回执路径
     * */
    private static final String CUS_RESULT_PATH = "cusResult/";

    /**
     * 报关单回执路径
     * */
    protected static final String FORM_CUS_RESULT_PATH = CUS_RESULT_PATH + "formCusResult/";

    /**
     * 通讯回执路径
     * */
    protected static final String DEC_MOD_CUS_RESULT_PATH = CUS_RESULT_PATH + "decModCusResult/";

    /**
     * 构造函数
     *
     * @param path 资源路
     * */
    protected Resource(String path) {
        this.path = ROOT_PATH + path;
    }

    /**
     * 获取资源输入流
     *
     * @return 资源输入流
     * */
    public final InputStream getInputStream() {
        if (this.inputStream != null) {
            return this.inputStream;
        }

        this.inputStream = this.getClass().getResourceAsStream(ROOT_PATH + path);

        return this.inputStream;
    }

    /**
     * 关闭资源流
     * */
    public final void closeInputStream() {
        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            } catch (IOException e) {
                throw new ErrorException(e.getMessage());
            }
        }
    }

}