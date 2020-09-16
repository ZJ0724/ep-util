package com.easipass.util.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.easipass.util.core.Database;
import com.easipass.util.core.ParamDbComparator;
import com.easipass.util.core.database.MdbDatabase;
import com.easipass.util.core.database.SWGDPARADatabase;
import com.easipass.util.core.exception.WarningException;
import com.easipass.util.core.paramDbComparator.ExcelImportParamDbComparator;
import com.easipass.util.core.paramDbComparator.ExportParamDbComparator;
import com.easipass.util.core.service.CusMessageService;
import com.easipass.util.core.util.ExcelUtil;

public class Main {

    public static void main(String[] args) throws Exception {

        CusMessageService.ComparisonMessage comparisonMessage = new CusMessageService.ComparisonMessage();

        comparisonMessage.setFlag(true);
        comparisonMessage.addMessage("12");
        comparisonMessage.addMessage("5757");

        System.out.println(comparisonMessage);

    }

}