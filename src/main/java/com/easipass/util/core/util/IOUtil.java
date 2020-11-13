package com.easipass.util.core.util;

import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.InfoException;
import java.io.*;

/**
 * IO流工具
 *
 * @author ZJ
 * */
public final class IOUtil {

    /**
     * 关闭流
     *
     * @param inputStream 输入流
     * */
    public static void close(InputStream inputStream) {
        if (inputStream == null) {
            return;
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 关闭流
     *
     * @param outputStream 输出流
     * */
    public static void close(OutputStream outputStream) {
        if (outputStream == null) {
            return;
        }

        try {
            outputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 获取输入流
     *
     * @param filePath 文件路径
     *
     * @return 输入流
     * */
    public static InputStream getInputStream(String filePath) {
        try {
            return new FileInputStream(new File(filePath));
        } catch (IOException e) {
            throw new InfoException(e.getMessage());
        }
    }

}