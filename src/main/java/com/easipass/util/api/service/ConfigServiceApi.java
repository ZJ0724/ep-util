package com.easipass.util.api.service;

import com.easipass.util.core.DTO.config.DaKaConfigDTO;
import com.easipass.util.core.VO.DaKaConfigVO;
import com.easipass.util.core.config.DaKaConfig;
import com.easipass.util.entity.Response;
import com.zj0724.util.springboot.parameterCheck.OpenParameterCheck;
import org.springframework.web.bind.annotation.*;

/**
 * 配置服务
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "config")
@OpenParameterCheck
public class ConfigServiceApi {

    /**
     * 设置打卡配置
     *
     * @return 响应
     * */
    @PostMapping("setDaKaConfig")
    public Response setDaKaConfig(@RequestBody DaKaConfigDTO daKaConfigDTO) {
        DaKaConfig.getInstance().setData(daKaConfigDTO);

        return Response.returnTrue();
    }

    /**
     * 获取打卡配置
     *
     * @return 响应
     * */
    @GetMapping("getDaKaConfig")
    public Response getDaKaConfig() {
        DaKaConfig daKaConfig = DaKaConfig.getInstance();

        daKaConfig.loadData();

        DaKaConfigVO daKaConfigVO = new DaKaConfigVO();

        daKaConfigVO.setData(daKaConfig);

        return Response.returnTrue(daKaConfigVO);
    }

}