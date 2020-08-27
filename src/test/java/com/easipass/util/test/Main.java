package com.easipass.util.test;

import com.easipass.util.core.ParamDbComparator;
import com.easipass.util.core.paramDbComparator.ExportParamDbComparator;
import com.easipass.util.core.paramDbComparator.ImportParamDbComparator;

public class Main {

    public static void main(String[] args) throws Exception {


        ParamDbComparator paramDbComparator = new ImportParamDbComparator();

        paramDbComparator.comparison("parameterDb.mdb", "C:\\Users\\ZJ\\Desktop\\参数库\\Update\\Data\\parameterDb.mdb");


//        ParamDbComparator paramDbComparator = new ExportParamDbComparator();
//
//
//        paramDbComparator.comparison("parameterDb.mdb", "C:\\Users\\ZJ\\Desktop\\参数库\\Update\\Data\\parameterDb.mdb");

    }

}