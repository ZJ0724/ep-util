package com.easipass.epUtil;

import com.easipass.epUtil.component.Http;
import com.easipass.epUtil.module.DaKaModule;

public class Test {

    public static void main(String[] args) throws Exception {

        Http http = new Http("http://localhost:8002/config/get", "GET", null);

        System.out.println(http.send());

    }

}