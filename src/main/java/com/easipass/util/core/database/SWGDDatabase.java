package com.easipass.util.core.database;

import com.easipass.util.core.C3p0Config;
import com.easipass.util.core.Database;
import com.easipass.util.core.config.SWGDDatabaseConfig;
import com.easipass.util.core.exception.ConnectionFailException;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.SearchException;
import com.easipass.util.core.util.StringUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * SWGD数据库
 *
 * @author ZJ
 * */
public final class SWGDDatabase extends Database {

    /**
     * c3p0连接池
     * */
    private static final ComboPooledDataSource COMBO_POOLED_DATA_SOURCE = new ComboPooledDataSource();

    /**
     * SWGDDatabaseConfig
     * */
    private static final SWGDDatabaseConfig SWGD_DATABASE_CONFIG = SWGDDatabaseConfig.getInstance();

    static {
        C3p0Config.getInstance().setData(COMBO_POOLED_DATA_SOURCE);
        try {
            COMBO_POOLED_DATA_SOURCE.setDriverClass(SWGD_DATABASE_CONFIG.driverClass);
        } catch (PropertyVetoException e) {
            throw new ErrorException(e.getMessage());
        }
        COMBO_POOLED_DATA_SOURCE.setJdbcUrl(SWGD_DATABASE_CONFIG.url);
        COMBO_POOLED_DATA_SOURCE.setUser(SWGD_DATABASE_CONFIG.username);
        COMBO_POOLED_DATA_SOURCE.setPassword(SWGD_DATABASE_CONFIG.password);
    }

    /**
     * log
     * */
    private static final Logger LOGGER = LoggerFactory.getLogger(SWGDDatabase.class);

    /**
     * 构造函数
     */
    public SWGDDatabase() {
        super(getConnection(), "SWGD");
    }

    /**
     * 设置修撤单文件名
     * */
    public void updateDecModFileName(String preEntryId, String fileName) {
        this.update("UPDATE SWGD.T_SWGD_DECMOD_HEAD SET FILE_NAME = ? WHERE PRE_ENTRY_ID = ?", preEntryId, fileName);
    }

    /**
     * 查询AGENT_CODE
     *
     * @param ediNo ediNo
     *
     * @return 表头的AGENT_CODE
     * */
    public String queryAgentCode(String ediNo) {
        return Database.getFiledData(this.query("SELECT AGENT_CODE FROM SWGD.T_SWGD_AGENT_LIST WHERE EDI_NO = ?", ediNo), "AGENT_CODE", true);
    }

    /**
     * 查询报关单表头数据
     *
     * @param ediNo ediNo编号
     * */
    public ResultSet queryFormHead(String ediNo) {
        return this.query("SELECT * FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?", ediNo);
    }

    /**
     * 查询表体数据
     *
     * @param ediNo ediNo编号
     * @param gNo gNo
     * */
    public ResultSet queryFormList(String ediNo, String gNo) {
        return this.query("SELECT * FROM SWGD.T_SWGD_FORM_LIST WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND G_NO = ?", ediNo, gNo);
    }

    /**
     * 查询集装箱数据
     *
     * @param ediNo ediNo编号
     * @param containerNo 集装箱序号
     * */
    public ResultSet queryFormContainer(String ediNo, String containerNo) {
        return this.query("SELECT * FROM SWGD.T_SWGD_FORM_CONTAINER WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND CONTAINER_NO = ?", ediNo, containerNo);
    }

    /**
     * 查询随附单证数据
     *
     * @param ediNo ediNo编号
     * @param certificateNo 随附单证序号
     * */
    public ResultSet queryFormCertificate(String ediNo, String certificateNo) {
        return this.query("SELECT * FROM SWGD.T_SWGD_FORM_CERTIFICATE WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND CERTIFICATE_NO = ?", ediNo, certificateNo);
    }

    /**
     * 查询申请单证数据
     *
     * @param ediNo ediNo编号
     * @param orderNo 序号
     * */
    public ResultSet queryDecRequestCert(String ediNo, String orderNo) {
        return this.query("SELECT * FROM SWGD.T_DEC_REQUEST_CERT WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND ORDER_NO = ?", ediNo, orderNo);
    }

    /**
     * 查询企业资质
     *
     * @param ediNo ediNo编号
     * @param orderNo 序号
     * */
    public ResultSet queryDecCopLimit(String ediNo, String orderNo) {
        return this.query("SELECT * FROM SWGD.T_DEC_COP_LIMIT WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND ORDER_NO = ?", ediNo, orderNo);
    }

    /**
     * 查询企业承诺
     *
     * @param ediNo ediNo编号
     * @param orderNo 序号
     * */
    public ResultSet queryDecCopPromise(String ediNo, String orderNo) {
        return this.query("SELECT * FROM SWGD.T_DEC_COP_PROMISE WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND ORDER_NO = ?", ediNo, orderNo);
    }

    /**
     * 查询其他包装
     *
     * @param ediNo ediNo编号
     * @param orderNo 序号
     * */
    public ResultSet queryDecOtherPack(String ediNo, String orderNo) {
        return this.query("SELECT * FROM SWGD.T_DEC_OTHER_PACK WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND ORDER_NO = ?", ediNo, orderNo);
    }

    /**
     * 获取报关单文件名
     *
     * @param ediNo ediNo编号
     * */
    public String queryFormHeadFileName(String ediNo) {
        return Database.getFiledData(this.query("SELECT * FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?", ediNo), "FILE_NAME", true);
    }

    /**
     * 获取修撤单文件名
     *
     * @param preEntryId 报关单号
     *
     * @return 修撤单文件名
     * */
    public String queryDecModFileName(String preEntryId) {
        return Database.getFiledData(this.query("SELECT * FROM SWGD.T_SWGD_DECMOD_HEAD WHERE PRE_ENTRY_ID = ?", preEntryId), "FILE_NAME", true);
    }

    /**
     * 查询产品资质
     *
     * @param ediNo ediNo
     * @param gNo 商品序号
     * @param orderNo 产品资质序号
     * */
    public ResultSet queryDecGoodsLimit(String ediNo, String gNo, String orderNo) {
        return this.query("SELECT * FROM SWGD.T_DEC_GOODS_LIMIT WHERE LIST_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_LIST WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND G_NO = ?) AND ORDER_NO = ?", ediNo, gNo, orderNo);
    }

    /**
     * 查询产品资质VIN
     *
     * @param DEC_GOODS_LIMIT_GUID DEC_GOODS_LIMIT_GUID
     * @param ORDER_NO ORDER_NO
     *
     * @return ResultSet
     * */
    public ResultSet queryDecGoodsLimitVin(String DEC_GOODS_LIMIT_GUID, String ORDER_NO) {
        return this.query("SELECT * FROM SWGD.T_DEC_GOODS_LIMIT_VIN WHERE DEC_GOODS_LIMIT_GUID = ? AND ORDER_NO = ?", DEC_GOODS_LIMIT_GUID, ORDER_NO);
    }

    /**
     * 查询特许权使用费
     *
     * @param ediNo ediNo
     *
     * @return ResultSet
     * */
    public ResultSet queryDecRoyaltyFee(String ediNo) {
        return this.query("SELECT * FROM SWGD.T_DEC_ROYALTY_FEE WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?)", ediNo);
    }

    /**
     * 查询ediNo
     *
     * @param preEntryId 报关单号
     *
     * @return ediNo
     * */
    public String queryEdiNoByPreEntryId(String preEntryId) {
        return Database.getFiledData(this.query("SELECT EDI_NO FROM SWGD.T_SWGD_FORM_HEAD WHERE PRE_ENTRY_ID = ?", preEntryId), "EDI_NO", true);
    }

    /**
     * 查询ediNo
     *
     * @param seqNo seqNo
     *
     * @return ediNo
     * */
    public String queryEdiNoBySeqNo(String seqNo) {
        return Database.getFiledData(this.query("SELECT EDI_NO FROM SWGD.T_SWGD_FORM_HEAD WHERE SEQ_NO = ?", seqNo), "EDI_NO", true);
    }

    /**
     * 查询修撤单表头
     *
     * @param preEntryId 报关单号
     *
     * @return ResultSet
     * */
    public ResultSet queryDecModHead(String preEntryId) {
        return this.query("SELECT * FROM SWGD.T_SWGD_DECMOD_HEAD WHERE PRE_ENTRY_ID = ?", preEntryId);
    }

    /**
     * 查询报关单表头通过报关单号
     *
     * @param preEntryId 报关单号
     *
     * @return ResultSet
     * */
    public ResultSet queryFormHeadByPreEntryId(String preEntryId) {
        return this.query("SELECT * FROM SWGD.T_SWGD_FORM_HEAD WHERE PRE_ENTRY_ID = ?", preEntryId);
    }

    /**
     * 获取修撤单表体
     *
     * @param preEntryId 报关单号
     * @param QPFieldCode 字段代码
     *
     * @return ResultSet
     * */
    public ResultSet queryDecModList(String preEntryId, String QPFieldCode) {
        return this.query("SELECT * FROM SWGD.T_SWGD_DECMOD_LIST WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_DECMOD_HEAD WHERE PRE_ENTRY_ID = ?) AND QP_FIELDCODE = ?", preEntryId, QPFieldCode);
    }

    /**
     * 获取第三方用户信息
     *
     * @param SENDER 发送方代码
     *
     * @return ResultSet
     * */
    public ResultSet querySupplyUser(String SENDER) {
        return this.query("SELECT * FROM SWGD.T_SWGD_SUPPLY_USER WHERE SENDER = ?", SENDER);
    }

    /**
     * 表头时候存在
     *
     * @param ediNo ediNo
     *
     * @return 存在返回true
     * */
    public boolean formHeadIsExist(String ediNo) {
        try {
            if (this.queryFormHead(ediNo).next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return false;
    }

    /**
     * 清空参数库表数据
     *
     * @param tableName 表名
     * */
    public void deleteParamDbTable(String tableName) {
        String sql = StringUtil.append("DELETE FROM SWGDPARA.", tableName);

        LOGGER.info(sql);

        this.update(sql);
    }

    /**
     * 查询报关单数据
     *
     * @param type type
     * @param data 数据
     *
     * @return 查询到的数据
     * */
    public static List<String> queryFormHead(String type, String data) {
        if ("0".equals(type)) {
            type = "EDI_NO";
        } else if ("1".equals(type)) {
            type = "PRE_ENTRY_ID";
        } else if ("2".equals(type)) {
            type = "SEQ_NO";
        } else {
            throw new SearchException("未找到对应类型");
        }

        if (data == null) {
            data = "";
        }

        List<String> result = new ArrayList<>();
        SWGDDatabase swgdDatabase = new SWGDDatabase();
        String sql = "SELECT * FROM (SELECT * FROM SWGD.T_SWGD_FORM_HEAD ORDER BY CREATE_TIME DESC) WHERE ROWNUM <= 5 AND " + type + " LIKE '%" + data + "%'";

        LOGGER.info(sql);

        try {
            ResultSet resultSet = swgdDatabase.query(sql);

            while (resultSet.next()) {
                result.add(getFiledData(resultSet, type));
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdDatabase.close();
        }

        return result;
    }

    /**
     * 搜索修撤单
     *
     * @param preEntryId preEntryId
     *
     * @return 修撤单集合
     * */
    public static List<String> searchDecMod(String preEntryId) {
        List<String> result = new ArrayList<>();
        SWGDDatabase swgdDatabase = new SWGDDatabase();

        if (preEntryId == null) {
            preEntryId = "";
        }

        try {
            ResultSet resultSet = swgdDatabase.query("SELECT * FROM (SELECT * FROM SWGD.T_SWGD_DECMOD_HEAD WHERE PRE_ENTRY_ID LIKE '%" + preEntryId + "%' ORDER BY CREATE_TIME DESC) WHERE ROWNUM <= 5");

            while (resultSet.next()) {
                result.add(SWGDDatabase.getFiledData(resultSet, "PRE_ENTRY_ID"));
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdDatabase.close();
        }

        return result;
    }

    /**
     * 搜索代理委托
     *
     * @param ediNo ediNo
     * */
    public static List<String> searchAgent(String ediNo) {
        List<String> result = new ArrayList<>();
        SWGDDatabase swgdDatabase = new SWGDDatabase();

        if (ediNo == null) {
            ediNo = "";
        }

        try {
            ResultSet resultSet = swgdDatabase.query("SELECT * FROM (SELECT * FROM SWGD.T_SWGD_AGENT_LIST WHERE EDI_NO LIKE '%" + ediNo + "%' ORDER BY CREATE_TIME DESC) WHERE ROWNUM <= 5");

            while (resultSet.next()) {
                result.add(SWGDDatabase.getFiledData(resultSet, "EDI_NO"));
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdDatabase.close();
        }

        return result;
    }

    /**
     * 获取连接
     *
     * @return Connection
     * */
    private static Connection getConnection() {
        try {
            // 验证driverCLass
            Class.forName(SWGD_DATABASE_CONFIG.driverClass);
            return COMBO_POOLED_DATA_SOURCE.getConnection();
        } catch (SQLException |ClassNotFoundException e) {
            throw new ConnectionFailException("SWGD数据库连接失败");
        }
    }

    /**
     * 获取参数库表名
     *
     * @param groupName 组名
     * @param fieldName 字段名
     *
     * @return 参数库表名
     * */
    public static List<String> getParamDbTables(String groupName, String fieldName) {
        SWGDDatabase swgdDatabase = new SWGDDatabase();
        List<String> result = new ArrayList<>();
        ResultSet resultSet = swgdDatabase.query("SELECT * FROM SWGDPARA.T_PARAMS_GROUP_TABLE WHERE GROUP_NAME = '" + groupName + "'");

        try {
            while (resultSet.next()) {
                String tableName = SWGDDatabase.getFiledData(resultSet, fieldName);
                result.add(tableName);
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdDatabase.close();
        }

        return result;
    }

    /**
     * 获取字段
     *
     * @param tableName 表名
     * @param fieldName 字段名
     *
     * @return 字段集合
     * */
    public static List<String> getParamDbFields(String tableName, String fieldName) {
        SWGDDatabase swgdDatabase = new SWGDDatabase();
        List<String> result = new ArrayList<>();
        ResultSet resultSet = swgdDatabase.query("SELECT * FROM SWGDPARA.T_PARAMS_MATCH WHERE TABLE_NAME = '" + tableName + "'");

        try {
            while (resultSet.next()) {
                String filed = SWGDDatabase.getFiledData(resultSet, fieldName);
                result.add(filed);
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdDatabase.close();
        }

        return result;
    }

    /**
     * 获取表版本
     *
     * @param tableName 表名
     *
     * @return 版本
     * */
    public static String getParamDbTableVersion(String tableName) {
        SWGDDatabase swgdDatabase = new SWGDDatabase();
        String version;

        try {
            ResultSet resultSet = swgdDatabase.query("SELECT * FROM SWGDPARA.T_PARAMS_VERSION_CURRENT WHERE TABLE_NAME = '"+ tableName + "'");

            if (resultSet.next()) {
                version = getFiledData(resultSet, "PARAMS_VERSION");
            } else {
                ResultSet resultSet1 = swgdDatabase.query("SELECT * FROM SWGDPARA.T_PARAMS_VERSION WHERE TABLE_NAME = '" + tableName + "'");
                int v = 0;

                while (resultSet1.next()) {
                    int v1 = Integer.parseInt(getFiledData(resultSet1, "PARAMS_VERSION"));

                    if (v1 > v) {
                        v = v1;
                    }
                }

                version = v + "";
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdDatabase.close();
        }

        return version;
    }

    /**
     * 获取参数库表数据量
     *
     * @param tableName 表名
     *
     * @return 数据量
     * */
    public static Integer getParamDbDataCount(String tableName) {
        String version = getParamDbTableVersion(tableName);

        if (StringUtil.isEmpty(version)) {
            return null;
        }

        SWGDDatabase swgdDatabase = new SWGDDatabase();

        try {
            // 数量
            String countS = getFiledData(swgdDatabase.query("SELECT COUNT(*) COUNT FROM SWGDPARA." + tableName + " WHERE PARAMS_VERSION = '" + version + "'"), "COUNT", true);

            if (StringUtil.isEmpty(countS)) {
                return 0;
            }

            return Integer.parseInt(countS);
        } finally {
            swgdDatabase.close();
        }
    }

}