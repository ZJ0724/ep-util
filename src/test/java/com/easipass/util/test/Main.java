package com.easipass.util.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) throws Exception {
        ComboPooledDataSource COMBO_POOLED_DATA_SOURCE = new ComboPooledDataSource();
        COMBO_POOLED_DATA_SOURCE.setJdbcUrl("jdbc:ucanaccess://C:\\Users\\14762\\Desktop\\下载\\parameterDb1.mdb");
        Connection connection = COMBO_POOLED_DATA_SOURCE.getConnection();
        System.out.println(connection);
    }

}