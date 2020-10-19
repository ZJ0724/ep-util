package com.easipass.util.core.util;

import com.easipass.util.core.exception.ErrorException;
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
     * @param append 是否追加数据
     * */
    public static void setData(File file, String data, boolean append) {
        try {
            OutputStream outputStream = new FileOutputStream(file, append);
            outputStream.write(data.getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 写入文件数据
     *
     * @param file 文件
     * @param data 数据
     * */
    public static void setData(File file, String data) {
        setData(file, data, false);
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
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                byte[] bytes = new byte[1024];
                int i = bufferedInputStream.read(bytes);
                while (i != -1) {
                    bufferedOutputStream.write(bytes, 0, i);
                    i = bufferedInputStream.read(bytes);
                }
                bufferedOutputStream.flush();
                outputStream.close();
                bufferedOutputStream.close();
                bufferedInputStream.close();
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
        StringBuilder result = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            if (!file.exists()) {
                return null;
            }
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);
            char[] chars = new char[1024];
            int i = bufferedReader.read(chars);
            while (i != -1) {
                result.append(new String(chars, 0, i));
                i = bufferedReader.read(chars);
            }
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result.toString();
    }

    /**
     * 创建文件
     *
     * @param file 文件
     * */
    public static void createFile(File file) {
        if (!file.exists()) {
            File p = file.getParentFile();

            if (!p.exists()) {
                if (!p.mkdirs()) {
                    throw new ErrorException("创建文件夹失败");
                }
            }

            try {
                if (!file.createNewFile()) {
                    throw new ErrorException("创建文件失败");
                }
            } catch (IOException e) {
                throw new ErrorException(e.getMessage());
            }
        }
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * */
    public static void delete(File file) {
        if (!file.delete()) {
            throw new ErrorException("删除文件失败");
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * */
    public static void delete(String filePath) {
        delete(new File(filePath));
    }

}