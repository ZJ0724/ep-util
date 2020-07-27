package com.easipass.epUtil.entity.oracle;

import com.easipass.epUtil.entity.Oracle;
import com.easipass.epUtil.entity.config.SWGDProperties;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.exception.OracleException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SWGD数据库
 *
 * @author ZJ
 * */
public final class SWGDOracle extends Oracle {

    /**
     * SWGD配置
     * */
    private static final SWGDProperties SWGD_PROPERTIES = SWGDProperties.getInstance();

    /**
     * 构造函数
     */
    public SWGDOracle() {
        super(SWGD_PROPERTIES.getUrl(), SWGD_PROPERTIES.getPort(), SWGD_PROPERTIES.getSid(), SWGD_PROPERTIES.getUsername(), SWGD_PROPERTIES.getPassword());
    }

    /**
     * 查询declPort
     * */
    public String queryDeclPort(String ediNo) {
        String declPort;

        this.connect();

        ResultSet resultSet = this.query("SELECT DECL_PORT FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?", new Object[]{ediNo});
        try {
            resultSet.next();
            declPort = resultSet.getString("DECL_PORT");
        } catch (SQLException e) {
            throw new OracleException("未找到报关单数据");
        } finally {
            this.close();
        }

        return declPort;
    }

    /**
     * 设置修撤单文件名
     * */
    public void updateDecModFileName(String preEntryId, String fileName) {
        this.connect();
        this.update("UPDATE SWGD.T_SWGD_DECMOD_HEAD SET FILE_NAME = ? WHERE PRE_ENTRY_ID = ?", new Object[]{fileName, preEntryId});
        this.close();
    }

    /**
     * 查询AGENT_CODE
     *
     * @param ediNo ediNo
     *
     * @return 表头的AGENT_CODE
     * */
    public String queryAgentCode(String ediNo) {
        String agentCode = null;

        this.connect();
        ResultSet resultSet = this.query("SELECT AGENT_CODE FROM SWGD.T_SWGD_AGENT_LIST WHERE EDI_NO = ?", new Object[]{ediNo});
        try {
            if (resultSet.next()) {
                agentCode = resultSet.getString("AGENT_CODE");
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            this.close();
        }

        return agentCode;
    }

    /**
     * 查询报关单表头数据
     *
     * @param ediNo ediNo编号
     * */
    public ResultSet queryFormHead(String ediNo) {
        ResultSet resultSet = this.query("SELECT * FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?", new Object[]{ediNo});

        try {
            if (!resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return resultSet;
    }

    /**
     * 查询表体数据
     *
     * @param ediNo ediNo编号
     * @param gNo gNo
     * */
    public ResultSet queryFormList(String ediNo, String gNo) {
        ResultSet resultSet = this.query("SELECT * FROM SWGD.T_SWGD_FORM_LIST WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND G_NO = ?", new Object[]{ediNo, gNo});

        try {
            if (!resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return resultSet;
    }

    /**
     * 查询集装箱数据
     *
     * @param ediNo ediNo编号
     * @param containerNo 集装箱序号
     * */
    public ResultSet queryFormContainer(String ediNo, String containerNo) {
        ResultSet resultSet = this.query("SELECT * FROM SWGD.T_SWGD_FORM_CONTAINER WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND CONTAINER_NO = ?", new Object[]{ediNo, containerNo});

        try {
            if (!resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return resultSet;
    }

    /**
     * 查询随附单证数据
     *
     * @param ediNo ediNo编号
     * @param certificateNo 随附单证序号
     * */
    public ResultSet queryFormCertificate(String ediNo, String certificateNo) {
        ResultSet resultSet = this.query("SELECT * FROM SWGD.T_SWGD_FORM_CERTIFICATE WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND CERTIFICATE_NO = ?", new Object[]{ediNo, certificateNo});

        try {
            if (!resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return resultSet;
    }

    /**
     * 查询申请单证数据
     *
     * @param ediNo ediNo编号
     * @param orderNo 序号
     * */
    public ResultSet queryDecRequestCert(String ediNo, String orderNo) {
        ResultSet resultSet = this.query("SELECT * FROM SWGD.T_DEC_REQUEST_CERT WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND ORDER_NO = ?", new Object[]{ediNo, orderNo});

        try {
            if (!resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return resultSet;
    }

    /**
     * 查询企业资质
     *
     * @param ediNo ediNo编号
     * @param orderNo 序号
     * */
    public ResultSet queryDecCopLimit(String ediNo, String orderNo) {
        ResultSet resultSet = this.query("SELECT * FROM SWGD.T_DEC_COP_LIMIT WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND ORDER_NO = ?", new Object[]{ediNo, orderNo});

        try {
            if (!resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return resultSet;
    }

    /**
     * 查询企业承诺
     *
     * @param ediNo ediNo编号
     * @param orderNo 序号
     * */
    public ResultSet queryDecCopPromise(String ediNo, String orderNo) {
        ResultSet resultSet = this.query("SELECT * FROM SWGD.T_DEC_COP_PROMISE WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND ORDER_NO = ?", new Object[]{ediNo, orderNo});

        try {
            if (!resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return resultSet;
    }

    /**
     * 查询其他包装
     *
     * @param ediNo ediNo编号
     * @param orderNo 序号
     * */
    public ResultSet queryDecOtherPack(String ediNo, String orderNo) {
        ResultSet resultSet = this.query("SELECT * FROM SWGD.T_DEC_OTHER_PACK WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND ORDER_NO = ?", new Object[]{ediNo, orderNo});

        try {
            if (!resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return resultSet;
    }

    /**
     * 获取报关单文件名
     *
     * @param ediNo ediNo编号
     * */
    public String queryFormHeadFileName(String ediNo) {
        this.connect();

        String result = null;
        ResultSet resultSet = this.query("SELECT * FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?", new Object[]{ediNo});

        try {
            if (resultSet.next()) {
                result = resultSet.getString("FILE_NAME");
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        this.close();

        return result;
    }

    /**
     * 获取修撤单文件名
     *
     * @param preEntryId 报关单号
     *
     * @return 修撤单文件名
     * */
    public String queryDecModFileName(String preEntryId) {
        this.connect();

        String result = null;
        ResultSet resultSet = this.query("SELECT * FROM SWGD.T_SWGD_DECMOD_HEAD WHERE PRE_ENTRY_ID = ?", new Object[]{preEntryId});

        try {
            if (resultSet.next()) {
                result = resultSet.getString("FILE_NAME");
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        this.close();

        return result;
    }

    /**
     * 查询产品资质
     *
     * @param ediNo ediNo
     * @param gNo 商品序号
     * @param orderNo 产品资质序号
     * */
    public ResultSet queryDecGoodsLimit(String ediNo, String gNo, String orderNo) {
        ResultSet resultSet = this.query("SELECT * FROM SWGD.T_DEC_GOODS_LIMIT WHERE LIST_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_LIST WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?) AND G_NO = ?) AND ORDER_NO = ?", new Object[]{ediNo, gNo, orderNo});

        try {
            if (!resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return resultSet;
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
        ResultSet resultSet = this.query("SELECT * FROM SWGD.T_DEC_GOODS_LIMIT_VIN WHERE DEC_GOODS_LIMIT_GUID = ? AND ORDER_NO = ?", new Object[]{DEC_GOODS_LIMIT_GUID, ORDER_NO});

        try {
            if (!resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return resultSet;
    }

    /**
     * 查询特许权使用费
     *
     * @param ediNo ediNo
     *
     * @return ResultSet
     * */
    public ResultSet queryDecRoyaltyFee(String ediNo) {
        ResultSet resultSet = this.query("SELECT * FROM SWGD.T_DEC_ROYALTY_FEE WHERE HEAD_ID = (SELECT ID FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?)", new Object[]{ediNo});

        try {
            if (!resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return resultSet;
    }

}