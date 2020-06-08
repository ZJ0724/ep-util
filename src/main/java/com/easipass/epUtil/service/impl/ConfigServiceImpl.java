package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.service.ConfigService;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Override
    public Response set(Map<String, Object> data) {
        // config
        Config config = Config.getConfig();

        // 设置数据
        config.setData(data);

        return Response.returnTrue();
    }

    @Override
    public Response get() {
        // config
        Config config = Config.getConfig();

        return Response.returnTrue(config);
    }

}