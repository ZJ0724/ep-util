package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.module.DaKa;
import com.easipass.epUtil.service.DaKaService;
import org.springframework.stereotype.Service;

@Service
public class DaKaServiceImpl implements DaKaService {

    /**
     * 打卡模块
     * */
    private final DaKa daKa = DaKa.getDaKa();

    @Override
    public Response start() {
        // 已开启打卡后不再次开启
        if (daKa.getStatus()) {
            return Response.returnFalse("已开启打卡");
        }

        daKa.start();

        return Response.returnTrue();
    }

    @Override
    public Response stop() {
        daKa.stop();

        return Response.returnTrue();
    }

    @Override
    public Response getLog() {
        return Response.returnTrue(daKa.getLog());
    }

    @Override
    public Response getStatus() {
        return Response.returnTrue(daKa.getStatus());
    }

}