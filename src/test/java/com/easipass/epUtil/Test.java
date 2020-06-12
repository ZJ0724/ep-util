package com.easipass.epUtil;


import com.easipass.epUtil.module.DaKaModule;

public class Test {

    public static void main(String[] args) throws Exception {


        DaKaModule daKaModule = DaKaModule.getDaKa();


        daKaModule.start();

        daKaModule.start();

    }

}