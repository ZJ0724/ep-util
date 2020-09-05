package com.easipass.util.test;

import com.easipass.util.core.Database;
import com.easipass.util.core.ParamDbComparator;
import com.easipass.util.core.database.MdbDatabase;
import com.easipass.util.core.paramDbComparator.ExcelImportParamDbComparator;
import com.easipass.util.core.paramDbComparator.ExportParamDbComparator;
import com.easipass.util.core.util.ExcelUtil;

public class Main {

    public static void main(String[] args) throws Exception {



        ExcelUtil excelUtil = new ExcelUtil("C:\\Users\\14762\\Desktop\\下载\\AGREEMENT_RATE_20200904(1).xlsx", 0);

        System.out.println(excelUtil.getAllRow());

    }

}