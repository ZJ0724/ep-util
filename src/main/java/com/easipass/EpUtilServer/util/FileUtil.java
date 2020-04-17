package com.easipass.EpUtilServer.util;

import com.easipass.EpUtilServer.exception.ErrorException;
import java.io.*;

public class FileUtil {

    /**
     * 复制其他文件
     * */
    public static void copyOtherFile(InputStream inputStream, String copyPath, String fileName) {
        try {
            File file = new File(copyPath, fileName);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new ErrorException("创建文件失败");
                }
            }
            OutputStream outputStream = new FileOutputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            byte[] bytes = new byte[bufferedInputStream.available()];
            int len = bufferedInputStream.read(bytes);
            bufferedOutputStream.write(bytes);
            outputStream.close();
            bufferedInputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 复制文本文件
     */
    public static void copyTextFile(InputStream inputStream, String copyPath, String fileName) {
        try {
            File file = new File(copyPath, fileName);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new ErrorException("创建文件失败");
                }
            }
            OutputStream outputStream = new FileOutputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            int len = inputStream.read(bytes);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
    }

}
