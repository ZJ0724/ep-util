package com.easipass.util.test;

import com.easipass.util.core.config.ParamDbMapping;
import com.easipass.util.core.database.MdbDatabase;
import com.easipass.util.core.database.SWGDDatabase;

public class Main {

    public static void main(String[] args) {


        MdbDatabase mdbDatabase = new MdbDatabase("C:\\Users\\ZJ\\Desktop\\下载\\test.mdb");



        System.out.println(mdbDatabase.getTableCount("123"));

    }

}