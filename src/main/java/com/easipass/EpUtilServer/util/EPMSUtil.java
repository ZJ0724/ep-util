package com.easipass.EpUtilServer.util;

import com.zj0724.StepWebDriver.entity.StepWebDriver;

public class EPMSUtil {

    /**
     * 点击job.swgdRecv.run
     * */
    public static void run(StepWebDriver stepWebDriver) {
        stepWebDriver.url("http://192.168.120.83:9909/console");
        stepWebDriver.findElementByCssSelector("input[tabindex='1']").sendKey("Testing");
        stepWebDriver.findElementByCssSelector("input[tabindex='2']").sendKey("Testing");
        stepWebDriver.findElementByCssSelector("button[tabindex='3']").click();
        stepWebDriver.await(1000);
        stepWebDriver.findElementByXpath("//span[text()=' Servers']").parent().parent().prev().children(1).click();
        stepWebDriver.findElementByXpath("//span[text()=' node.server']").click();
        stepWebDriver.findElementByXpath("//div[text()='job.swgdRecv']").parent().next().next().next().children(1).children(1).children(1).children(3).children(1).click();
    }

}
