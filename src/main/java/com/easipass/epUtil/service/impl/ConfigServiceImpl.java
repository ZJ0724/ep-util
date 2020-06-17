package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.service.ConfigService;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    /** config */
    private final Config config = Config.getConfig();

    @Override
    public Response set(String data) {
        // 设置数据
        config.setData(data);

        return Response.returnTrue();
    }

    @Override
    public Response get() {
        return Response.returnTrue(config);
    }

}