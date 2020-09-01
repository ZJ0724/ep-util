package com.easipass.util.test;

import com.easipass.util.core.ParamDbComparator;
import com.easipass.util.core.paramDbComparator.ExcelImportParamDbComparator;
import com.easipass.util.core.paramDbComparator.ExportParamDbComparator;
import com.easipass.util.core.util.ExcelUtil;

public class Main {

    public static void main(String[] args) throws Exception {



        ParamDbComparator paramDbComparator = new ExcelImportParamDbComparator("CIQ_CODE", "C:\\Users\\ZJ\\Desktop\\参数库\\参数库更新\\2020-08-28\\dim_eciq_z_bbd_ciq_code_20200826.xlsx");


        paramDbComparator.comparison();

    }

}