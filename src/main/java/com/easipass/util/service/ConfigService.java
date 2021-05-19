package com.easipass.util.service;

import com.easipass.util.entity.po.ConfigPO;
import org.springframework.stereotype.Service;

@Service
public interface ConfigService {

    /**
     * 获取配置
     *
     * @param code code
     *
     * @return ConfigPO
     * */
    ConfigPO getByCode(ConfigPO.Code code);

}