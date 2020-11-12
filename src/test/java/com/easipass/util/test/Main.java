package com.easipass.util.test;

import com.easipass.util.core.Http;
import com.easipass.util.core.component.AccessDatabase;
import com.easipass.util.core.component.Excel;
import com.easipass.util.core.component.SWGDPARADatabase;
import com.easipass.util.core.config.ParamDbTableMappingConfig;
import com.easipass.util.core.config.SWGDPARAFileConfig;
import com.easipass.util.core.entity.DatabaseInfo;
import com.easipass.util.core.exception.InfoException;
import com.easipass.util.core.service.ParamDbService;
import com.easipass.util.core.util.JdbcUtil;
import com.healthmarketscience.jackcess.*;
import com.healthmarketscience.jackcess.impl.DatabaseImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {




        System.out.println(new ParamDbService().mdbExport());
        System.out.println("完成");
    }

}