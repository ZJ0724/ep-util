package com.easipass.util.test;

import com.easipass.util.core.component.AccessDatabase;
import com.easipass.util.core.component.SWGDPARADatabase;
import com.easipass.util.core.config.SWGDPARAFileConfig;
import com.easipass.util.core.entity.DatabaseInfo;

public class Main {

    public static void main(String[] args) {


        System.out.println(SWGDPARADatabase.getInstance().getTableData("USE_TO", "10000"));
    }

}