package com.easipass.util.test;

import com.easipass.util.core.ParamDbComparator;

public class Main {

    public static void main(String[] args) throws Exception {


        ParamDbComparator paramDbComparator = ParamDbComparator.getInstance();


        System.out.println(paramDbComparator.importComparison("parameterDb.mdb", "C:\\Users\\ZJ\\Desktop\\参数库\\备份\\parameterDb.mdb"));
    }

}