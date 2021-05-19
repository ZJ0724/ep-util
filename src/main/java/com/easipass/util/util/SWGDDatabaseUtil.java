package com.easipass.util.util;

import com.easipass.util.Main;
import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.service.ConfigService;
import com.zj0724.common.component.Jdbc;
import com.zj0724.common.component.jdbc.OracleJdbc;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.StringUtil;
import java.util.List;
import java.util.Map;

public final class SWGDDatabaseUtil {

    /**
     * 查询数据
     *
     * @param sql sql
     *
     * @return List<Map<String, Object>>
     * */
    public static List<Map<String, Object>> query(String sql) {
        Jdbc jdbc = null;
        try {
            jdbc = getJdbc();
            return jdbc.queryBySql(sql);
        } finally {
            if (jdbc != null) {
                jdbc.close();
            }
        }
    }

    /**
     * 获取报关单数据
     *
     * @param ediNo ediNo
     *
     * @return Map<String, Object>
     * */
    public static Map<String, Object> getFormHead(String ediNo) {
        List<Map<String, Object>> formHeadList = query("SELECT * FROM T_SWGD_FORM_HEAD WHERE EDI_NO = '" + ediNo + "'");
        if (formHeadList.size() == 0) {
            return null;
        }
        if (formHeadList.size() > 1) {
            throw new InfoException("存在多个相同编号的报关单");
        }
        return formHeadList.get(0);
    }

    private static Jdbc getJdbc() {
        ConfigService configService = Main.APPLICATION_CONTEXT.getBean(ConfigService.class);
        ConfigPO hostConfigPO = configService.getByCode(ConfigPO.Code.SWGD_DATABASE_HOST);
        ConfigPO portConfigPO = configService.getByCode(ConfigPO.Code.SWGD_DATABASE_PORT);
        ConfigPO sidConfigPO = configService.getByCode(ConfigPO.Code.SWGD_DATABASE_SID);
        ConfigPO usernameConfigPO = configService.getByCode(ConfigPO.Code.SWGD_DATABASE_USERNAME);
        ConfigPO passwordConfigPO = configService.getByCode(ConfigPO.Code.SWGD_DATABASE_PASSWORD);
        if (hostConfigPO == null || StringUtil.isEmpty(hostConfigPO.getData())) {
            throw new InfoException("SWGD数据库地址未配置");
        }
        if (portConfigPO == null || StringUtil.isEmpty(portConfigPO.getData())) {
            throw new InfoException("SWGD数据库端口未配置");
        }
        if (sidConfigPO == null || StringUtil.isEmpty(sidConfigPO.getData())) {
            throw new InfoException("SWGD数据库sid未配置");
        }
        if (usernameConfigPO == null || StringUtil.isEmpty(usernameConfigPO.getData())) {
            throw new InfoException("SWGD数据库账号未配置");
        }
        if (passwordConfigPO == null || StringUtil.isEmpty(passwordConfigPO.getData())) {
            throw new InfoException("SWGD数据库密码未配置");
        }
        return new OracleJdbc(hostConfigPO.getData(), Integer.parseInt(portConfigPO.getData()), sidConfigPO.getData(), usernameConfigPO.getData(), passwordConfigPO.getData());
    }

}