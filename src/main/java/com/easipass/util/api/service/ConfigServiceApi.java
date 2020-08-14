package com.easipass.util.api.service;

import com.easipass.util.core.DTO.CusResultUploadSftpConfigDTO;
import com.easipass.util.core.DTO.DaKaConfigDTO;
import com.easipass.util.core.DTO.SWGDDatabaseConfigDTO;
import com.easipass.util.core.VO.CusResultUploadSftpConfigVO;
import com.easipass.util.core.VO.DaKaConfigVO;
import com.easipass.util.core.VO.SWGDDatabaseConfigVO;
import com.easipass.util.core.config.CusResultUploadSftpConfig;
import com.easipass.util.core.config.DaKaConfig;
import com.easipass.util.core.config.SWGDDatabaseConfig;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.*;

/**
 * 配置服务
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "config")
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

    /**
     * 设置SWGD数据库配置
     *
     * @return 响应
     * */
    @PostMapping("setSWGDDatabaseConfig")
    public Response setSWGDDatabaseConfig(@RequestBody SWGDDatabaseConfigDTO swgdConfigDTO) {
        SWGDDatabaseConfig swgdDatabaseConfig = SWGDDatabaseConfig.getInstance();

        swgdDatabaseConfig.setData(swgdConfigDTO);

        return Response.returnTrue();
    }

    /**
     * 获取SWGD数据库配置
     *
     * @return 响应
     * */
    @GetMapping("getSWGDDatabaseConfig")
    public Response getSWGDDatabaseConfig() {
        SWGDDatabaseConfig swgdDatabaseConfig = SWGDDatabaseConfig.getInstance();

        swgdDatabaseConfig.loadData();

        SWGDDatabaseConfigVO swgdDatabaseConfigVO = new SWGDDatabaseConfigVO();

        swgdDatabaseConfigVO.setData(swgdDatabaseConfig);

        return Response.returnTrue(swgdDatabaseConfigVO);
    }

    /**
     * 设置回执上传sftp配置
     *
     * @return 响应
     * */
    @PostMapping("setCusResultUploadSftpConfig")
    public Response setCusResultUploadSftpConfig(@RequestBody CusResultUploadSftpConfigDTO cusResultUploadSftpConfigDTO) {
        CusResultUploadSftpConfig cusResultUploadSftpConfig = CusResultUploadSftpConfig.getInstance();

        cusResultUploadSftpConfig.setData(cusResultUploadSftpConfigDTO);

        return Response.returnTrue();
    }

    /**
     * 获取回执上传sftp配置
     *
     * @return 响应
     * */
    @GetMapping("getCusResultUploadSftpConfig")
    public Response getCusResultUploadSftpConfig() {
        CusResultUploadSftpConfig cusResultUploadSftpConfig = CusResultUploadSftpConfig.getInstance();

        cusResultUploadSftpConfig.loadData();

        CusResultUploadSftpConfigVO cusResultUploadSftpConfigVO = new CusResultUploadSftpConfigVO();

        cusResultUploadSftpConfigVO.setData(cusResultUploadSftpConfig);

        return Response.returnTrue(cusResultUploadSftpConfigVO);
    }

}