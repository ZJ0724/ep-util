package com.easipass.EpUtilServer.util;

import com.zj0724.uiAuto.WebDriver;

public class EPMSUtil {

    /**
     * 点击job.swgdRecv.run
     * */
    public static void run(WebDriver webDriver) {
        webDriver.url("http://192.168.120.83:9909/console");
        webDriver.findElementByCssSelector("input[tabindex='1']").sendKey("Testing");
        webDriver.findElementByCssSelector("input[tabindex='2']").sendKey("Testing");
        webDriver.findElementByCssSelector("button[tabindex='3']").click();
        webDriver.await(1000);
        webDriver.findElementByXpath("//span[text()=' Servers']").parent().parent().prev().children(1).click();
        webDriver.findElementByXpath("//span[text()=' node.server']").click();
        webDriver.findElementByXpath("//div[text()='job.swgdRecv']").parent().next().next().next().children(1).children(1).children(1).children(3).children(1).click();
    }

}
