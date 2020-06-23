package com.easipass.epUtil.service;

import com.easipass.epUtil.entity.Response;
import org.springframework.stereotype.Service;

/**
 * 系统服务
 *
 * @author ZJ
 * */
@Service
public interface SystemService {

    /**
     * 获取版本信息
     * */
    Response getVersion();

}