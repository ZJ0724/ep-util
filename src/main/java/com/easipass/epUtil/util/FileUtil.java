package com.easipass.epUtil.util;

import com.easipass.epUtil.exception.ErrorException;
import java.io.*;
import java.nio.charset.StandardCharsets;

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

    /**
     * 获取文件内容
     * */
    public static String getData(File file) {
        try {
            StringBuilder result = new StringBuilder();
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                result.append(line);
                line = bufferedReader.readLine();
            }
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
            return result.toString();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 写入文件数据
     * */
    public static void setData(File file, String data) {
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }
    }

    /**
     * 获取文件数据
     *
     * @param inputStream 输入流
     *
     * @return 输入流数据
     * */
    public static String getData(InputStream inputStream) {
        try {
            StringBuilder result = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                result.append(line);
                line = bufferedReader.readLine();
            }
            inputStreamReader.close();
            bufferedReader.close();
            return result.toString();
        } catch (IOException e) {
            return null;
        }
    }

}