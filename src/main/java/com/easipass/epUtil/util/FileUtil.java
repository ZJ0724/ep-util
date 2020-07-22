package com.easipass.epUtil.util;

import com.easipass.epUtil.exception.ErrorException;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 文件工具
 *
 * @author ZJ
 * */
public final class FileUtil {

    /**
     * 写入文件数据
     *
     * @param file 文件
     * @param data 数据
     * */
    public static void setData(File file, String data) {
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 检查生成文件
     *
     * @param file 要生成的文件
     * @param inputStream 文件流
     * */
    public static void createFile(File file, InputStream inputStream) {
        if (file == null) {
            return;
        }

        if (!file.exists()) {
            File par = file.getParentFile();

            if (!par.exists()) {
                boolean is = par.mkdirs();
                if (!is) {
                    throw new ErrorException("创建文件夹失败");
                }
            }

            try {
                OutputStream outputStream = new FileOutputStream(file);
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                outputStream.write(bytes);
                outputStream.close();
            } catch (IOException e) {
                throw new ErrorException(e.getMessage());
            }
        }
    }

    /**
     * 获取文件数据
     *
     * @param file 文件
     *
     * @return 文件内容
     * */
    public static String getData(File file) {
        InputStream inputStream = null;
        String result;

        try {
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            result = new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            result = null;
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new ErrorException(e.getMessage());
            }
        }

        if (result == null) {
            throw new ErrorException("获取文件数据异常");
        }

        return result;
    }

}