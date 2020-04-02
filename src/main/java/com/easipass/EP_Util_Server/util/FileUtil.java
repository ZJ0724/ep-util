package com.easipass.EP_Util_Server.util;

import com.easipass.EP_Util_Server.exception.BugException;
import java.io.*;

public class FileUtil {

    /**
     * 获取文件中所有数据
     */
    public static String getData(File file){
        try {
            String result="";
            InputStream inputStream=new FileInputStream(file);
            byte[] bytes=new byte[inputStream.available()];
            inputStream.read(bytes);
            result=new String(bytes,"UTF-8");
            inputStream.close();
            return result;
        }catch (IOException e){
            throw new BugException(e.getMessage());
        }
    }

    /**
     * 获取文件数据通过流
     * */
    public static String getData(InputStream inputStream){
        try {
            String result="";
            byte[] bytes=new byte[inputStream.available()];
            inputStream.read(bytes);
            result=new String(bytes,"UTF-8");
            inputStream.close();
            return result;
        }catch (IOException e){
            throw new BugException(e.getMessage());
        }
    }

    /**
     * 覆盖写入
     */
    public static void setData(File file,String data){
        try {
            OutputStream outputStream=new FileOutputStream(file);
            outputStream.write(data.getBytes("UTF-8"));
            outputStream.close();
        }catch (IOException e){
            throw new BugException(e.getMessage());
        }
    }

    /**
     * 复制文件
     */
    public static void copyFile(File file,String path) {
        try {
            File outfile = new File(path, file.getName());
            if (!outfile.getParentFile().exists()) {
                outfile.getParentFile().mkdirs();
            }
            OutputStream outputStream = new FileOutputStream(outfile);
            outputStream.write(FileUtil.getData(file).getBytes("UTF-8"));
            outputStream.close();
        }catch (IOException e){
            throw new BugException(e.getMessage());
        }
    }

}
