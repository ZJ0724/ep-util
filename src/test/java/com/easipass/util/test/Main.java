package com.easipass.util.test;

import com.easipass.util.core.config.SWGDPARAFileConfig;
import com.easipass.util.core.entity.DatabaseInfo;

public class Main {

    public static void main(String[] args) {
        System.out.println(new DatabaseInfo(SWGDPARAFileConfig.currentFile));
    }

}