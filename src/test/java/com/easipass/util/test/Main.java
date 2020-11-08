package com.easipass.util.test;

import com.easipass.util.core.component.AccessDatabase;
import com.easipass.util.core.component.SWGDPARADatabase;
import com.easipass.util.core.config.SWGDPARAFileConfig;
import com.easipass.util.core.entity.DatabaseInfo;

public class Main {

    public static void main(String[] args) {


        System.out.println(SWGDPARADatabase.getInstance().isPrimaryKey("USE_TO", "PARAMS_VERSION"));
    }

}