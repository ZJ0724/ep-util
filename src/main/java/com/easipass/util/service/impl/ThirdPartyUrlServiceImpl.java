package com.easipass.util.service.impl;

import com.easipass.util.component.Database;
import com.easipass.util.entity.po.ThirdPartyUrlPO;
import com.easipass.util.service.ThirdPartyUrlService;
import com.zj0724.common.entity.QueryResult;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public final class ThirdPartyUrlServiceImpl implements ThirdPartyUrlService {

    @Override
    public List<ThirdPartyUrlPO> getAll() {
        Database<ThirdPartyUrlPO> database = Database.getDatabase(ThirdPartyUrlPO.class);
        QueryResult<ThirdPartyUrlPO> query = database.query(null);
        return query.getData();
    }

}