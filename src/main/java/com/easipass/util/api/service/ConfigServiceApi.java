package com.easipass.util.api.service;

import com.easipass.util.core.DTO.config.CusResultUploadSftpConfigDTO;
import com.easipass.util.core.DTO.config.DaKaConfigDTO;
import com.easipass.util.core.DTO.config.ParamDbMappingDTO;
import com.easipass.util.core.DTO.config.SWGDDatabaseConfigDTO;
import com.easipass.util.core.VO.CusResultUploadSftpConfigVO;
import com.easipass.util.core.VO.DaKaConfigVO;
import com.easipass.util.core.VO.SWGDDatabaseConfigVO;
import com.easipass.util.core.config.CusResultUploadSftpConfig;
import com.easipass.util.core.config.DaKaConfig;
import com.easipass.util.core.config.ParamDbMapping;
import com.easipass.util.core.config.SWGDDatabaseConfig;
import com.easipass.util.entity.Response;
import com.zj0724.util.springboot.parameterCheck.NotNull;
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
     * paramDbMapping
     * */
    private static final String PARAM_DB_MAPPING = "paramDbMapping/";

    /**
     * PARAM_DB_MAPPING_INSTANCE
     * */
    private static final ParamDbMapping PARAM_DB_MAPPING_INSTANCE = ParamDbMapping.getInstance();

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

    /**
     * 获取ParamDbMapping
     *
     * @return Response
     * */
    @GetMapping(PARAM_DB_MAPPING + "get")
    public Response getParamDbMapping() {
        return Response.returnTrue(PARAM_DB_MAPPING_INSTANCE.getData());
    }

    /**
     * 添加ParamDbMapping
     *
     * @param paramDbMappingDTO paramDbMappingDTO
     *
     * @return Response
     * */
    @PostMapping(PARAM_DB_MAPPING + "add")
    public Response addParamDbMapping(@RequestBody @NotNull ParamDbMappingDTO paramDbMappingDTO) {
        PARAM_DB_MAPPING_INSTANCE.add(new ParamDbMapping.Row(paramDbMappingDTO.getDbName(), paramDbMappingDTO.getMdbName()));

        return Response.returnTrue();
    }

    /**
     * 删除ParamDbMapping
     *
     * @param index 索引
     *
     * @return Response
     * */
    @PostMapping(PARAM_DB_MAPPING + "delete")
    public Response deleteParamDbMapping(@RequestParam @NotNull String index) {
        PARAM_DB_MAPPING_INSTANCE.delete(index);

        return Response.returnTrue();
    }

    /**
     * 修改ParamDbMapping
     *
     * @param index index
     * @param paramDbMappingDTO paramDbMappingDTO
     *
     * @return Response
     * */
    @PostMapping(PARAM_DB_MAPPING + "update")
    public Response updateParamDbMapping(@RequestParam @NotNull String index, @RequestBody @NotNull ParamDbMappingDTO paramDbMappingDTO) {
        PARAM_DB_MAPPING_INSTANCE.update(index, new ParamDbMapping.Row(paramDbMappingDTO.getDbName(), paramDbMappingDTO.getMdbName()));

        return Response.returnTrue();
    }

    /**
     * 获取ParamDbMapping一共多少条
     *
     * @return Response
     * */
    @GetMapping(PARAM_DB_MAPPING + "getCount")
    public Response paramDbMappingGetCount() {
        return Response.returnTrue(PARAM_DB_MAPPING_INSTANCE.getCount());
    }

}