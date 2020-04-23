package com.easipass.EpUtilServer.util;

import com.easipass.EpUtilServer.exception.ErrorException;
import java.io.*;

public class FileUtil {

    /**
     * 复制其他文件
     * */
    public static void copyOtherFile(InputStream inputStream, File outFile) {
        try {
            OutputStream outputStream = new FileOutputStream(outFile);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            byte[] bytes = new byte[bufferedInputStream.available()];
            int len = bufferedInputStream.read(bytes);
            bufferedOutputStream.write(bytes);
            outputStream.close();
            bufferedInputStream.close();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 复制文本文件
     */
    public static void copyTextFile(InputStream inputStream, File outputFile) {
        try {
            OutputStream outputStream = new FileOutputStream(outputFile);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            outputStream.write(bytes);
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
    }

}
