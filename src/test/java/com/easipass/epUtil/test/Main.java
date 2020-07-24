package com.easipass.epUtil.test;

import com.easipass.epUtil.entity.ChromeDriver;
import com.easipass.epUtil.util.ThreadUtil;

public class Main {

    public static void main(String[] args) {
        ChromeDriver.kill();

//        testChromeDriverPool();
    }

    public static void testChromeDriverPool() {
        ChromeDriver.openChromeDriverPool();

        ThreadUtil.sleep(20000);

        ChromeDriver.get();
    }

}