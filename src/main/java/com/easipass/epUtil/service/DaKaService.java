package com.easipass.epUtil.service;

import com.easipass.epUtil.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface DaKaService {

    /**
     * 开启打卡
     * */
    Response start();

    /**
     * 停止打卡
     * */
    Response stop();

    /**
     * 获取日志
     * */
    Response getLog();

    /**
     * 获取当前打卡状态
     * */
    Response getStatus();

    /**
     * 手动打卡
     * */
    Response manualDaKa();

}