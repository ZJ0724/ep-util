package com.easipass.util.test;

import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.Database;
import com.easipass.util.core.database.SWGDPARADatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class databaseTest {

    public static void main(String[] args) throws Exception {
        // 开发库
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.131.52:1521:dev12c", "SWGDPARA", "easipass");
        Database database = new Database(connection) {};

        // DANG_CHEMICAL_HS_CIQ表数据
        List<JSONObject> data = database.queryToJson("SELECT * FROM SWGDPARA.DANG_CHEMICAL_HS_CIQ");

        for (JSONObject jsonpObject : data) {
            // HS_CODE
            String HS_CODE = jsonpObject.getString("HS_CODE");
            // CIQ_NAME
            String CIQ_NAME = jsonpObject.getString("CIQ_NAME");

            // 获取测试环境CIQ_CODE
            SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
            List<JSONObject> jsonObjects = swgdparaDatabase.queryToJson("SELECT * FROM SWGDPARA.CIQ_CODE WHERE PARAMS_VERSION = (SELECT PARAMS_VERSION FROM (SELECT PARAMS_VERSION FROM SWGDPARA.CIQ_CODE GROUP BY PARAMS_VERSION ORDER BY PARAMS_VERSION DESC) WHERE ROWNUM = 1) AND HS_CODE = '" + HS_CODE + "' AND GOODS_NAME = '" + CIQ_NAME + "'");
            if (jsonObjects.size() == 0) {
                CIQ_NAME = CIQ_NAME.replaceAll("\\(", "（").replaceAll("\\)", "）");
                jsonObjects = swgdparaDatabase.queryToJson("SELECT * FROM SWGDPARA.CIQ_CODE WHERE PARAMS_VERSION = (SELECT PARAMS_VERSION FROM (SELECT PARAMS_VERSION FROM SWGDPARA.CIQ_CODE GROUP BY PARAMS_VERSION ORDER BY PARAMS_VERSION DESC) WHERE ROWNUM = 1) AND HS_CODE = '" + HS_CODE + "' AND GOODS_NAME = '" + CIQ_NAME + "'");
            }
            swgdparaDatabase.close();
            if (jsonObjects.size() == 0) {
                continue;
            }
            String CIQ_CODE = jsonObjects.get(0).getString("EXPAND_NO");

            // 设置开发环境CIQ_CODE
            database.update("UPDATE SWGDPARA.DANG_CHEMICAL_HS_CIQ SET CIQ_CODE = '" + CIQ_CODE + "' WHERE ID = '" + jsonpObject.getString("ID") + "'");
        }

        System.out.println("完成");
    }

}