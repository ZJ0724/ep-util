package com.easipass.util.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.easipass.util.core.Database;
import com.easipass.util.core.ParamDbComparator;
import com.easipass.util.core.database.MdbDatabase;
import com.easipass.util.core.database.SWGDPARADatabase;
import com.easipass.util.core.entity.ErrorLog;
import com.easipass.util.core.exception.WarningException;
import com.easipass.util.core.paramDbComparator.ExcelImportParamDbComparator;
import com.easipass.util.core.paramDbComparator.ExportParamDbComparator;
import com.easipass.util.core.service.CusMessageService;
import com.easipass.util.core.service.SystemService;
import com.easipass.util.core.util.ExcelUtil;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println(MdbDatabase.getTableCount("C:\\Users\\ZJ\\Desktop\\下载C:\\Users\\ZJ\\Desktop\\下载\\new20201015\\ClassifyDb.mdb", "classify"));
    }

}