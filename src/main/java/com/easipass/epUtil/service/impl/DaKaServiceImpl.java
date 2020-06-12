package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.module.DaKaModule;
import com.easipass.epUtil.service.DaKaService;
import org.springframework.stereotype.Service;

@Service
public class DaKaServiceImpl implements DaKaService {

    /**
     * 打卡模块
     * */
    private final DaKaModule daKaModule = DaKaModule.getDaKa();

    @Override
    public Response start() {
        // 已开启打卡后不再次开启
        if (daKaModule.getStatus()) {
            return Response.returnFalse("已开启打卡");
        }

        daKaModule.start();

        return Response.returnTrue();
    }

    @Override
    public Response stop() {
        daKaModule.stop();

        return Response.returnTrue();
    }

    @Override
    public Response getLog() {
        return Response.returnTrue(daKaModule.getLog());
    }

    @Override
    public Response getStatus() {
        return Response.returnTrue(daKaModule.getStatus());
    }

}