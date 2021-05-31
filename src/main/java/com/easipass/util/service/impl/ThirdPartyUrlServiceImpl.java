package com.easipass.util.service.impl;

import com.easipass.util.component.Database;
import com.easipass.util.entity.po.ThirdPartyUrlPO;
import com.easipass.util.service.ThirdPartyUrlService;
import com.zj0724.common.entity.QueryResult;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.JsonUtil;
import com.zj0724.common.util.StringUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public final class ThirdPartyUrlServiceImpl implements ThirdPartyUrlService {

    @Override
    public List<ThirdPartyUrlPO> getAll() {
        Database<ThirdPartyUrlPO> database = Database.getDatabase(ThirdPartyUrlPO.class);
        QueryResult<ThirdPartyUrlPO> query = database.query(null);
        return query.getData();
    }

    @Override
    public void save(ThirdPartyUrlPO thirdPartyUrlPO) {
        // 校验
        if (thirdPartyUrlPO == null) {
            throw new InfoException("参数缺失");
        }
        if (StringUtil.isEmpty(thirdPartyUrlPO.getUrl())) {
            throw new InfoException("url不能为空");
        }
        if (StringUtil.isEmpty(thirdPartyUrlPO.getNote())) {
            throw new InfoException("备注不能为空");
        }
        if (!StringUtil.isEmpty(thirdPartyUrlPO.getRequestData())) {
            try {
                JsonUtil.parseObject(thirdPartyUrlPO.getRequestData(), Map.class);
            } catch (Exception e) {
                throw new InfoException("请求参数非json格式");
            }
        }

        Database<ThirdPartyUrlPO> database = Database.getDatabase(ThirdPartyUrlPO.class);
        database.save(thirdPartyUrlPO);
    }

    @Override
    public void delete(Long id) {
        Database<ThirdPartyUrlPO> database = Database.getDatabase(ThirdPartyUrlPO.class);
        database.deleteById(id);
    }

}