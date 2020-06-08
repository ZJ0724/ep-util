package com.easipass.epUtil;

import com.easipass.epUtil.entity.ChromeDriver;
import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.Result;
import com.easipass.epUtil.entity.result.formResult.TongXunFormResult;
import com.easipass.epUtil.entity.sftp.Sftp83;

import java.util.LinkedHashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws Exception {


        Config config = Config.getConfig();
        config.loadData();

        System.out.println(config.makeString());
//
//        Map<String, String> data = new LinkedHashMap<>();
//        data.put("SWGDUrl", "123");
//
//        config.setData(data);
//
//        config.loadData();
//        System.out.println(config.makeString());



    }

}