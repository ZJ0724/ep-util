package com.easipass.util.service;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ThirdPartyService {

    /**
     * 发送请求
     *
     * @param userCode 用户代码
     * @param url 请求地址
     * @param requestData 请求数据
     *
     * @return 响应数据
     * */
    String send(String userCode, String url, String requestData);

    /**
     * 获取用户
     *
     * @return List<String>
     * */
    List<String> getUsers();

}