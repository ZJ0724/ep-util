package com.easipass.epUtil;

import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.config.KSDDBConfig;
import com.easipass.epUtil.entity.config.SWGDConfig;
import com.easipass.epUtil.util.DateUtil;

public class Test {

    public static void main(String[] args) {

        KSDDBConfig ksddbConfig = KSDDBConfig.getKSDDBConfig();

        ksddbConfig.load();

        KSDDBConfig ksddbConfig1 = KSDDBConfig.getKSDDBConfig();

        System.out.println(ksddbConfig1);

    }

}
