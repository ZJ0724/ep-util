package com.easipass.util.test;

import com.easipass.util.core.component.AccessDatabase;
import com.easipass.util.core.component.Excel;
import com.easipass.util.core.component.SWGDPARADatabase;
import com.easipass.util.core.config.ParamDbTableMappingConfig;
import com.easipass.util.core.config.SWGDPARAFileConfig;
import com.easipass.util.core.entity.DatabaseInfo;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Excel excel = new Excel("C:\\Users\\ZJ\\Desktop\\file\\1.xlsx", 0);
        List<List<String>> allData = excel.getAllData();
        System.out.println();
    }

}