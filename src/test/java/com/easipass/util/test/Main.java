package com.easipass.util.test;

import com.easipass.util.core.ParamDbComparator;
import com.easipass.util.core.config.ParamDbMapping;
import com.easipass.util.core.database.MdbDatabase;
import com.easipass.util.core.database.SWGDDatabase;

import java.sql.ResultSet;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {




//        SWGDDatabase swgdDatabase = new SWGDDatabase();


//        List<ParamDbMapping.Row> rows = ParamDbMapping.getInstance().getRows();
//
//
//        for (ParamDbMapping.Row row : rows) {
////            swgdDatabase.deleteParamDbTable(row.getDbName());
//        }
//
//        swgdDatabase.close();


//        swgdDatabase.query("");




//        int i = 0;
//
//        ResultSet resultSet = mdbDatabase.query("SELECT TRADE_MODE, DISTRICT_T, BASIC_EX, BASIC_IM, EX_CONTROL, IM_CONTROL FROM TRADE_MO GROUP BY TRADE_MODE, DISTRICT_T, BASIC_EX, BASIC_IM, EX_CONTROL, IM_CONTROL HAVING(COUNT(*)) > 1");
//
//        while (resultSet.next()) {
//            i++;
//        }
//
//        System.out.println(i);
//
//        mdbDatabase.close();



        String[] fields = new String[]{"G_NAME", "CODE_T", "CODE_S", "CLASS_SPEC", "CLASS_NO", "KEY_NAME"};

        String dbName = "CLASSIFY";

        String mdbName = "classify";


        MdbDatabase mdbDatabase = new MdbDatabase("C:\\Users\\ZJ\\Desktop\\参数库\\备份\\parameterDb.mdb");

        SWGDDatabase swgdDatabase = new SWGDDatabase();

        ResultSet resultSet = mdbDatabase.query("SELECT * FROM " + mdbName);

        while (resultSet.next()) {

            String[] dataList = new String[fields.length];

            for (int i = 0; i < fields.length; i++) {
                dataList[i] = resultSet.getString(fields[i]).replaceAll("'", "''");
            }

            String sql = "SELECT * FROM SWGDPARA." + dbName + " WHERE 1 = 1";


            for (int i = 0; i < fields.length; i++) {
                if (!isNull(dataList[i])) {
                    sql = sql + " AND " + fields[i] + " = '" + dataList[i] + "'";
                }
            }

            ResultSet resultSet1 = swgdDatabase.query(sql);

            if (!resultSet1.next()) {
                System.out.println("错误sql: " + sql);
            }

//            swgdDatabase.close();
        }



    }

    static boolean isNull(String s) {
        if (s.replaceAll(" ", "").equals("")) {
            return true;
        }
        return false;
    }

}