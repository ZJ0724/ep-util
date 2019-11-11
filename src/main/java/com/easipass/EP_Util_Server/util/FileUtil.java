package com.easipass.EP_Util_Server.util;

import java.io.*;

public class FileUtil {

    /**
     * 获取文件中所有数据
     */
    public static String getData(File file) throws IOException{

        String result="";
        InputStream inputStream=new FileInputStream(file);
        byte[] bytes=new byte[inputStream.available()];
        inputStream.read(bytes);
        result=new String(bytes,"UTF-8");
        inputStream.close();
        return result;

    }

    /**
     * 获取文件数据通过流
     * */
    public static String getData(InputStream inputStream) throws IOException{

        String result="";
        byte[] bytes=new byte[inputStream.available()];
        inputStream.read(bytes);
        result=new String(bytes,"UTF-8");
        inputStream.close();
        return result;

    }

    /**
     * 覆盖写入
     */
    public static void setData(File file,String data) throws IOException{

        OutputStream outputStream=new FileOutputStream(file);
        outputStream.write(data.getBytes("UTF-8"));
        outputStream.close();

    }

    /**
     * 复制文件
     */
    public static void copyFile(File file,String path) throws IOException {

        File outfile = new File(path, file.getName());
        if (!outfile.getParentFile().exists()) {
            outfile.getParentFile().mkdirs();
        }
        OutputStream outputStream = new FileOutputStream(outfile);
        outputStream.write(FileUtil.getData(file).getBytes("UTF-8"));
        outputStream.close();

    }

}
