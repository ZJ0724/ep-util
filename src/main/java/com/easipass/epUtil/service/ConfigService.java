package com.easipass.epUtil.service;

import com.easipass.epUtil.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface ConfigService {

    /**
     * 设置config
     *
     * @param data config完整数据
     *
     * @return 响应
     * */
    Response set(String data);

    /**
     * 获取config数据
     * */
    Response get();

}