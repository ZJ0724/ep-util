package com.easipass.util.core.config;

import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.Resource;
import com.easipass.util.core.util.FileUtil;
import com.easipass.util.core.util.StringUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 参数库映射
 *
 * @author ZJ
 * */
public final class ParamDbMapping {

    /**
     * 数据
     * */
    private List<Row> rows = new ArrayList<>();

    /**
     * 单例
     * */
    private static final ParamDbMapping PARAM_DB_MAPPING = new ParamDbMapping();

    /**
     * 文件
     * */
    private static File FILE;

    /**
     * 构造函数
     * */
    private ParamDbMapping() {
        FILE = new File(Resource.PARAM_DB_MAPPING.getPath());

        if (!FILE.exists()) {
            FileUtil.createFile(FILE);
            setDefaultData();
            commit();
        }

        this.getData();
    }

    /**
     * 提交
     * */
    private void commit() {
        String data = "";

        for (Row row : this.rows) {
            data = StringUtil.append(data, row.getData(), "\n");
        }

        data = data.substring(0, data.length() - 1);

        FileUtil.setData(FILE, data);
    }

    /**
     * 获取数据
     * */
    public List<JSONObject> getData() {
        String[] rows = FileUtil.getData(FILE).split("\n");

        this.rows = new ArrayList<>();

        for (String row : rows) {
            if (!StringUtil.isEmpty(row)) {
                this.rows.add(new Row(row));
            }
        }

        List<JSONObject> jsonObjects = new ArrayList<>();

        for (Row row : this.rows) {
            jsonObjects.add(row.getJson());
        }

        return jsonObjects;
    }

    /**
     * 设置默认数据
     * */
    private void setDefaultData() {
        this.rows.add(new Row("AREA_PRE", "AREA_PRE"));
        this.rows.add(new Row("CAR_HS", "CAR_HS"));
        this.rows.add(new Row("CAR_OTHER", "CAR_OTHER"));
        this.rows.add(new Row("CHARACTERISTIC_CODE", "CHARACTERISTICCODE"));
        this.rows.add(new Row("CHINA_REGIONALISM", "ChinaRegionalism"));
        this.rows.add(new Row("CIQ_CODE", "CiqCode"));
        this.rows.add(new Row("CIQ_ORG", "CiqOrg"));
        this.rows.add(new Row("CLASSIFY", "classify"));
        this.rows.add(new Row("CLASSIFY_NEW_EN", "Classify_New"));
        this.rows.add(new Row("CO_TYPE", "CO_TYPE"));
        this.rows.add(new Row("COMPLEX", "complex"));
        this.rows.add(new Row("CONDITION_CHECK", "condition_check"));
        this.rows.add(new Row("CONDITION_CHECK_STRING", "condition_check_string"));
        this.rows.add(new Row("CONTA_MODEL_STD", "ContainerModelStd"));
        this.rows.add(new Row("CORRELATION_REASON", "CorrelationReason"));
        this.rows.add(new Row("COUNTRY", "COUNTRY"));
        this.rows.add(new Row("COUNTRY_STD", "CountryStd"));
        this.rows.add(new Row("CTA_INF_REC", "CTA_INF_REC"));
        this.rows.add(new Row("CTA_MES_REC", "CTA_MES_REC"));
        this.rows.add(new Row("CTA_NAM_REC", "CTA_NAM_REC"));
        this.rows.add(new Row("CURR", "CURR"));
        this.rows.add(new Row("CURR_STD", "CurrStd"));
        this.rows.add(new Row("CUSTOMS", "CUSTOMS"));
        this.rows.add(new Row("DANG_PACK_SPEC", "DangPackSpec"));
        this.rows.add(new Row("DANG_PACK_TYPE", "DangPackType"));
        this.rows.add(new Row("DECLARATION_MATERIAL_CODE", "DeclarationMaterialCode"));
        this.rows.add(new Row("DISTRICT", "DISTRICT"));
        this.rows.add(new Row("DOMESTIC_PORT", "DomesticPort"));
        this.rows.add(new Row("EDOC_CODE", "Edoc_Code"));
        this.rows.add(new Row("EPCOMPARE_WD_SWGD", "EPCompare_wd_swgd"));
        this.rows.add(new Row("EXCHSOUR", "EXCHSOUR"));
        this.rows.add(new Row("FEE_MARK", "Fee_Mark"));
        this.rows.add(new Row("GOODS_ATTR", "GoodsAttr"));
        this.rows.add(new Row("HS_301", "HS_301"));
        this.rows.add(new Row("HZZS_COMPANY", "hzzs_Company"));
        this.rows.add(new Row("HZZS_CUSTOM", "hzzs_Custom"));
        this.rows.add(new Row("IC_CARD_LIMIT", "ICCardLimit"));
        this.rows.add(new Row("IMPORT_SURTAXES", "IMPORT_SURTAXES"));
        this.rows.add(new Row("INSUR_MARK", "Insur_Mark"));
        this.rows.add(new Row("IOF_CERT_BILL", "IOFCertBill"));
        this.rows.add(new Row("LC_TYPE", "LC_TYPE"));
        this.rows.add(new Row("LEVYMODE", "LEVYMODE"));
        this.rows.add(new Row("LEVYTYPE", "LEVYTYPE"));
        this.rows.add(new Row("LICENSED", "LICENSED"));
        this.rows.add(new Row("LICENSEN", "LICENSEN"));
        this.rows.add(new Row("CLIENT_MAPPING", "Mapping"));
        this.rows.add(new Row("OTHER_MARK", "Other_Mark"));
        this.rows.add(new Row("PAY_MODE", "PAY_MODE"));
        this.rows.add(new Row("PORT", "PORT"));
        this.rows.add(new Row("PORT_STD", "PortStd"));
        this.rows.add(new Row("PRODUCT_PERMISSION_TYPE", "ProductPermissionType"));
        this.rows.add(new Row("QTY_1_RANGE", "qty_1_range"));
        this.rows.add(new Row("QUATA", "QUATA"));
        this.rows.add(new Row("SPEC_DECL", "SpecDecl"));
        this.rows.add(new Row("SPECIALC", "SPECIALC"));
        this.rows.add(new Row("TABLE_INFO", "TableInfo"));
        this.rows.add(new Row("TAX_TYPE", "TAXTYPE"));
        this.rows.add(new Row("TRADE_MODE", "TRADE"));
        this.rows.add(new Row("TRADE_MO", "TRADE_MO"));
        this.rows.add(new Row("TRADE_MODE_STD", "TradeModeStd"));
        this.rows.add(new Row("TRAF_MODE_STD", "TrafModeStd"));
        this.rows.add(new Row("TRANS_FLAG", "TRANS_FLAG"));
        this.rows.add(new Row("TRANSAC", "TRANSAC"));
        this.rows.add(new Row("TRANSF", "TRANSF"));
        this.rows.add(new Row("UNIT", "UNIT"));
        this.rows.add(new Row("UNIT_STD", "UnitStd"));
        this.rows.add(new Row("USE_TO", "USE_TO"));
        this.rows.add(new Row("USE_PURPOSE", "UsePurpose"));
        this.rows.add(new Row("Z_CCM_WORLDFIRSTDISTRICT", "WorldFirstDistrict"));
        this.rows.add(new Row("WRAP_TYPE", "WRAP_TYPE"));
        this.rows.add(new Row("WRAP_TYPE_STD", "WrapTypeStd"));
        this.rows.add(new Row("YLXQ_CHECK", "YLQX_check"));
    }

    /**
     * 添加数据
     *
     * @param row row
     * */
    public void add(Row row) {
        this.rows.add(row);
        commit();
    }

    /**
     * 删除
     *
     * @param index 索引
     * */
    public void delete(String index) {
        int i;

        try {
            i = Integer.parseInt(index);
        } catch (NumberFormatException e) {
            return;
        }

        if (i > this.rows.size()) {
            return;
        }

        this.rows.remove(i);

        commit();
    }

    /**
     * 修改
     *
     * @param index 索引
     * @param row row
     * */
    public void update(String index, Row row) {
        int i;

        try {
            i = Integer.parseInt(index);
        } catch (NumberFormatException e) {
            return;
        }

        if (i > this.rows.size()) {
            return;
        }

        this.rows.get(i).setData(row);
        commit();
    }

    /**
     * 获取单例
     *
     * @return ParamDbMapping
     * */
    public static ParamDbMapping getInstance() {
        return PARAM_DB_MAPPING;
    }

    /**
     * row
     * */
    public final static class Row {
        /**
         * 数据库表名
         * */
        private String dbName;

        /**
         * mdb文件表名
         * */
        private String mdbName;

        private Row(String row) {
            String[] strings = row.replaceAll("\r", "").split(" ");

            this.dbName = strings[0];
            this.mdbName = strings[1];
        }

        public Row(String dbName, String mdbName) {
            this.dbName = dbName;
            this.mdbName = mdbName;
        }

        /**
         * 获取数据
         * */
        private String getData() {
            return this.dbName + " " + this.mdbName;
        }

        /**
         * get json
         * */
        private JSONObject getJson() {
            JSONObject jsonObject = new JSONObject(true);

            jsonObject.put("dbName", this.dbName);
            jsonObject.put("mdbName", this.mdbName);

            return jsonObject;
        }

        public String getDbName() {
            return dbName;
        }

        public String getMdbName() {
            return mdbName;
        }

        /**
         * 重新设置数据
         * */
        private void setData(Row row) {
            this.dbName = row.dbName;
            this.mdbName = row.mdbName;
        }
    }

}