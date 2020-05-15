package com.easipass.epUtil.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {

    /**
     * 编码
     */
    public static String encode(String s) {
        Base64.Encoder encoder = Base64.getEncoder();
        String result = "";
        result = encoder.encodeToString(s.getBytes(StandardCharsets.UTF_8));
        return result;
    }

    /**
     * 解码
     */
    public static String decode(String s){
        Base64.Decoder decoder = Base64.getDecoder();
        String result = "";
        result = new String(decoder.decode(s), StandardCharsets.UTF_8);
        return result;
    }

}
