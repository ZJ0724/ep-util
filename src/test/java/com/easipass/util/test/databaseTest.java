package com.easipass.util.test;

import com.easipass.util.core.database.SWGDDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class databaseTest {

    public static void main(String[] args) throws Exception {

        SWGDDatabase swgdDatabase = new SWGDDatabase();


        PreparedStatement preparedStatement = swgdDatabase.getPreparedStatement("SELECT * FROM SWGDPARA.CLASSIFY");

        preparedStatement.close();
        preparedStatement.close();

    }

}