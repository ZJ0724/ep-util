package com.easipass.util.core.util;

import com.easipass.util.core.exception.ErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

}