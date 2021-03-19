package com.easipass.util.service.impl;

import com.easipass.util.component.Database;
import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.service.ConfigService;
import com.zj0724.common.entity.Query;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public final class ConfigServiceImpl implements ConfigService {

    @Override
    public ConfigPO getByCode(ConfigPO.Code code) {
        Database<ConfigPO> configPODatabase = Database.getDatabase(ConfigPO.class);
        Query query = new Query();
        query.addFilter("CODE", code.name());
        Database.QueryResult<ConfigPO> query1 = configPODatabase.query(query);
        List<ConfigPO> data = query1.getData();
        if (data.size() == 0) {
            return null;
        } else {
            return data.get(0);
        }
    }

}