package com.easipass.EP_Util_Server.util;

import com.easipass.EP_Util_Server.exception.BugException;
import java.io.IOException;
import java.util.Base64;

public class Base64Util {

    /**
     * 编码
     */
    public static String encode(String s) {
        try {
            Base64.Encoder encoder=Base64.getEncoder();
            String result="";
            result=encoder.encodeToString(s.getBytes("UTF-8"));
            return result;
        }catch (IOException e){
            throw new BugException(e.getMessage());
        }
    }

    /**
     * 解码
     */
    public static String decode(String s){
        try {
            Base64.Decoder decoder=Base64.getDecoder();
            String result="";
            result=new String(decoder.decode(s),"UTF-8");
            return result;
        }catch (IOException e){
            throw new BugException(e.getMessage());
        }
    }

}