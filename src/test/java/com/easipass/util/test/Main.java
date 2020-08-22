package com.easipass.util.test;

import com.easipass.util.core.ParamDbComparator;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {


        ParamDbComparator paramDbComparator = ParamDbComparator.getInstance();

        System.out.println(paramDbComparator.importComparison("test", "C:\\Users\\ZJ\\Desktop\\参数库\\备份\\parameterDb.mdb"));
    }

}