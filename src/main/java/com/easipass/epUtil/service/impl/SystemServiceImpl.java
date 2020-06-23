package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.module.SystemModule;
import com.easipass.epUtil.service.SystemService;
import org.springframework.stereotype.Service;

/**
 * 系统服务实现
 *
 * @author ZJ
 * */
@Service
public class SystemServiceImpl implements SystemService {

    /** 系统模块 */
    private final SystemModule systemModule = SystemModule.getVersionModuleInstance();

    @Override
    public Response getVersion() {
        return Response.returnTrue(systemModule.getVersion());
    }

}