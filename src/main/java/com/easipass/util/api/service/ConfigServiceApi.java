package com.easipass.util.api.service;

import com.easipass.util.entity.DTO.DaKaConfigDTO;
import com.easipass.util.entity.DTO.SWGDConfigDTO;
import com.easipass.util.entity.DTO.Sftp83ConfigDTO;
import com.easipass.util.entity.Response;
import com.easipass.util.entity.VO.DaKaConfigVO;
import com.easipass.util.entity.VO.SWGDConfigVO;
import com.easipass.util.entity.VO.Sftp83ConfigVO;
import com.easipass.util.entity.config.DaKaProperties;
import com.easipass.util.entity.config.SWGDProperties;
import com.easipass.util.entity.config.Sftp83Properties;
import org.springframework.web.bind.annotation.*;

/**
 * 配置服务
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL +  "config")
public class ConfigServiceApi {

    /**
     * 设置打卡配置
     *
     * @return 响应
     * */
    @PostMapping("setDaKa")
    public Response setDaKa(@RequestBody DaKaConfigDTO daKaConfigDTO) {
        DaKaProperties.getInstance().setDataByDTO(daKaConfigDTO);

        return Response.returnTrue();
    }

    /**
     * 获取打卡配置
     *
     * @return 响应
     * */
    @GetMapping("getDaKa")
    public Response getDaKa() {
        return Response.returnTrue(new DaKaConfigVO());
    }

    /**
     * 设置SWGD配置
     *
     * @return 响应
     * */
    @PostMapping("setSWGD")
    public Response setSWGD(@RequestBody SWGDConfigDTO swgdConfigDTO) {
        SWGDProperties.getInstance().setDataByDTO(swgdConfigDTO);

        return Response.returnTrue();
    }

    /**
     * 获取SWGD配置
     *
     * @return 响应
     * */
    @GetMapping("getSWGD")
    public Response getSWGD() {
        return Response.returnTrue(new SWGDConfigVO());
    }

    /**
     * 设置sftp83配置
     *
     * @return 响应
     * */
    @PostMapping("setSftp83")
    public Response setSftp83(@RequestBody Sftp83ConfigDTO sftp83ConfigDTO) {
        Sftp83Properties.getInstance().setDataByDTO(sftp83ConfigDTO);

        return Response.returnTrue();
    }

    /**
     * 获取sftp83配置
     *
     * @return 响应
     * */
    @GetMapping("getSftp83")
    public Response getSftp83() {
        return Response.returnTrue(new Sftp83ConfigVO());
    }

}