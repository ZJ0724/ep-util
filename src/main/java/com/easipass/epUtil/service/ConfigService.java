package com.easipass.epUtil.service;

import com.easipass.epUtil.entity.Response;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public interface ConfigService {

    /**
     * 设置config
     *
     * @param data config完整数据
     *
     * @return 响应
     * */
    Response set(Map<String, Object> data);

    /**
     * 获取config数据
     * */
    Response get();

}