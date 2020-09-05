package com.easipass.util.test;

import com.easipass.util.core.Database;
import com.easipass.util.core.ParamDbComparator;
import com.easipass.util.core.database.MdbDatabase;
import com.easipass.util.core.exception.WarningException;
import com.easipass.util.core.paramDbComparator.ExcelImportParamDbComparator;
import com.easipass.util.core.paramDbComparator.ExportParamDbComparator;
import com.easipass.util.core.util.ExcelUtil;

public class Main {

    public static void main(String[] args) throws Exception {

        ExcelUtil excelUtil = new ExcelUtil("C:\\Users\\ZJ\\Desktop\\参数库\\参数库更新\\2020-09-04\\AGREEMENT_RATE_20200904.xlsx", 0);
    }

}