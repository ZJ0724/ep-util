package com.easipass.util.test;

import com.easipass.util.core.component.AccessDatabase;
import com.easipass.util.core.component.Excel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\ZJ\\Desktop\\file\\parameterdb2.mdb";
        try {
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}