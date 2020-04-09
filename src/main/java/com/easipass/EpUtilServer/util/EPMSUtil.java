package com.easipass.EpUtilServer.util;

import com.easipass.EpUtilServer.exception.ErrorException;
import pers.ZJ.UiAuto.Step;
import pers.ZJ.UiAuto.exception.ExecuteException;
import pers.ZJ.UiAuto.step.UiStep;
import java.io.File;

public class EPMSUtil {

    /**
     * 点击job.swgdRecv.run
     * */
    public static void run() {
        Step step = new UiStep(EPMSUtil.getChromeDriverFile());
        EPMSUtil.runOfStep(step);
        step.closeWebDriver();
    }

    public static void runOfStep(Step step) {
        try {
            step.execute("url(\"http://192.168.120.83:9909/console\")");
            step.execute("$(\"input[tabindex='1']\").sendKeys(\"Testing\")");
            step.execute("$(\"input[tabindex='2']\").sendKeys(\"Testing\")");
            step.execute("$(\"button[tabindex='3']\").click()");
            step.execute("wait(1000)");
            step.execute("$(\"span[text()=' Servers']\").parent().parent().prev().children(1).click()");
            step.execute("$(\"span[text()=' node.server']\").click()");
            step.execute("$(\"div[text()='job.swgdRecv']\").parent().next().next().children(1).children(1).children(1).children(3).children(1).click()");
        } catch (ExecuteException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 谷歌驱动
     * */
    public static File getChromeDriverFile() {
        return new File(System.getProperty("user.dir"), "/chromeDriver/chromedriver.exe");
    }

}
