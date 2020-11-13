package com.easipass.util.core.util;

import com.easipass.util.core.exception.InfoException;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * HttpUtil
 *
 * @author ZJ
 * */
public final class HttpUtil {

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @param httpServletResponse 响应
     * @param fileName 文件名
     * */
    public static void downLoadFile(String filePath, HttpServletResponse httpServletResponse, String fileName) {
        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(filePath);
            outputStream = httpServletResponse.getOutputStream();
            byte[] bytes = new byte[2014];
            int read = inputStream.read(bytes);
            while (read != -1) {
                outputStream.write(bytes);
                read = inputStream.read(bytes);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new InfoException(e.getMessage());
        } finally {
            IOUtil.close(inputStream);
            IOUtil.close(outputStream);
        }
    }

}