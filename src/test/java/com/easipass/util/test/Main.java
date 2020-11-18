package com.easipass.util.test;

import com.easipass.util.core.component.AccessDatabase;
import com.easipass.util.core.component.Excel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
//        AccessDatabase accessDatabase = new AccessDatabase("C:\\Users\\ZJ\\Desktop\\file\\test.mdb");
//
//        List<Map<String, Object>> complex = accessDatabase.getTableData("complex");
//
//        System.out.println();

        double d = 1.0E13;
        System.out.println(d);

        String s = new BigDecimal(d + "").toPlainString();

        double v = Double.parseDouble(s);
        System.out.println(v);
    }

}