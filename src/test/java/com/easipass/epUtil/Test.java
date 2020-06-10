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

        System.out.println(config);

//        String data = "{\n" +
//                "    \"SWGD\": {\n" +
//                "\t\t\"url\": \"192.168.130.216123\",\n" +
//                "\t\t\"port\": 1521,\n" +
//                "        \"sid\": \"testeport\",\n" +
//                "        \"username\": \"devtester\",\n" +
//                "        \"password\": \"easytester\"\n" +
//                "    },\n" +
//                "    \"sftp\": {\n" +
//                "        \"url\": \"192.168.120.83\",\n" +
//                "        \"port\": 22,\n" +
//                "        \"username\": \"gccoper\",\n" +
//                "        \"password\": \"gccoper\",\n" +
//                "        \"uploadPath\": \"/gcchome/winx/cus/cfg_c2e\"\n" +
//                "    }\n" +
//                "}";
//
//
//        config.setData(data);
//
//
//        System.out.println(config);


    }

}