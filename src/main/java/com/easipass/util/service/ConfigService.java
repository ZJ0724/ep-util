package com.easipass.util.service;

import com.easipass.util.entity.po.ConfigPO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ConfigService {

    /**
     * 获取配置
     *
     * @param code code
     *
     * @return ConfigPO
     * */
    ConfigPO getByCode(String code);

    ConfigPO getByCode(ConfigPO.Code code);

    /**
     * 获取所有
     *
     * @return List<ConfigPO>
     * */
    List<ConfigPO> getAll();

    /**
     * 保存
     *
     * @param configPO configPO
     * */
    void save(ConfigPO configPO);

}