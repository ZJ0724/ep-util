package com.easipass.util.service.impl;

import com.easipass.util.component.Database;
import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.service.ConfigService;
import com.zj0724.common.entity.Query;
import com.zj0724.common.entity.QueryResult;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.StringUtil;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public final class ConfigServiceImpl implements ConfigService {

    @Override
    public ConfigPO getByCode(String code) {
        if (StringUtil.isEmpty(code)) {
            throw new InfoException("code不能为空");
        }
        Database<ConfigPO> configPODatabase = Database.getDatabase(ConfigPO.class);
        Query query = new Query();
        query.addFilter("CODE", code);
        QueryResult<ConfigPO> query1 = configPODatabase.query(query);
        List<ConfigPO> data = query1.getData();
        if (data.size() == 0) {
            return null;
        } else {
            return data.get(0);
        }
    }

    @Override
    public ConfigPO getByCode(ConfigPO.Code code) {
        return getByCode(code.name());
    }

    @Override
    public List<ConfigPO> getAll() {
        List<ConfigPO> result = new ArrayList<>();
        for (ConfigPO.Code code : ConfigPO.Code.values()) {
            ConfigPO configPO = getByCode(code);
            if (configPO == null) {
                configPO = new ConfigPO();
                configPO.setCode(code.name());
            }
            configPO.setNote(code.getNote());
            result.add(configPO);
        }
        return result;
    }

    @Override
    public void save(ConfigPO configPO) {
        if (configPO == null) {
            throw new InfoException("参数不能为空");
        }

        String code = configPO.getCode();
        ConfigPO configPO1 = getByCode(code);
        if (configPO1 == null) {
            configPO1 = new ConfigPO();
        }

        ConfigPO.Code byCode = ConfigPO.Code.getByCode(code);
        if (byCode == null) {
            throw new InfoException("code：" + code + "未找到");
        }
        configPO1.setCode(code);
        configPO1.setNote(byCode.getNote());
        configPO1.setData(configPO.getData());

        Database<ConfigPO> configPODatabase = Database.getDatabase(ConfigPO.class);
        configPODatabase.save(configPO1);
    }

}