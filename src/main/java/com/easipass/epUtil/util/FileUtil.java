package com.easipass.epUtil.util;

import com.easipass.epUtil.exception.ErrorException;
import java.io.*;

public class FileUtil {

    /**
     * 复制其他文件
     * */
    public static void copyOtherFile(InputStream inputStream, File outFile) {
        try {
            File parentFile = outFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            OutputStream outputStream = new FileOutputStream(outFile);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            byte[] bytes = new byte[bufferedInputStream.available()];
            int len = bufferedInputStream.read(bytes);
            bufferedOutputStream.write(bytes);
            outputStream.close();
            bufferedInputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }
    }

    /**
     * 复制文本文件
     */
    public static void copyTextFile(InputStream inputStream, File outputFile) {
        try {
            File parentFile = outputFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            OutputStream outputStream = new FileOutputStream(outputFile);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }
    }

}