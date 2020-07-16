package com.easipass.epUtil.core.entity;

import com.easipass.epUtil.core.exception.ErrorException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * http组件
 *
 * @author ZJ
 */
public class Http {

    /** 请求地址 */
    private final String url;

    /** 请求类型 */
    private final String type;

    /** 请求头数据 */
    private final Map<String, String> headers = new LinkedHashMap<>();

    /**
     * 构造函数
     * */
    public Http(String url, String type) {
        this.url = url;
        this.type = type;

        // 默认请求头
        headers.put("accept", "*/*");
        headers.put("connection", "Keep-Alive");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
        if (this.type.equals("POST")) {
            headers.put("content-type", "application/json");
        }
    }

    /**
     * 设置请求头
     *
     * @param key 键
     * @param value 值
     * */
    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    /**
     * 发送请求
     *
     * @param data 请求数据
     *
     * @return 响应结果
     * */
    public String send(String data) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // 设置type
            httpURLConnection.setRequestMethod(this.type);

            // 设置请求头
            Set<String> keys = this.headers.keySet();
            for (String key : keys) {
                httpURLConnection.setRequestProperty(key, this.headers.get(key));
            }

            // 开启输入输出
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            // 输出数据
            OutputStream outputStream = null;
            if (data != null) {
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(data.getBytes());
            }

            // 获取响应数据
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }

            //关闭流
            if (outputStream != null) {
                outputStream.close();
            }
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();

            //断开连接
            httpURLConnection.disconnect();

            return stringBuilder.toString();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
    }

}