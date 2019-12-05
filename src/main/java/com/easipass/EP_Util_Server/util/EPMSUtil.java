package com.easipass.EP_Util_Server.util;

import pers.ZJ.UiAuto.Step;
import pers.ZJ.UiAuto.exception.BugException;
import pers.ZJ.UiAuto.exception.ExecuteException;
import pers.ZJ.UiAuto.step.UiStep;
import java.io.File;

public class EPMSUtil {

    /**
     * 点击job.swgdRecv.run
     * */
    public static void run(File chromeDriver){
        try {
            Step step=new UiStep(chromeDriver);
            step.openWebDriver();
            step.execute("url(\"http://192.168.120.83:9909/console\")");
            step.execute("$(\"input[tabindex='1']\").sendKeys(\"Testing\")");
            step.execute("$(\"input[tabindex='2']\").sendKeys(\"Testing\")");
            step.execute("$(\"button[tabindex='3']\").click()");
            step.execute("wait(1000)");
            step.execute("$(\"span[text()=' Servers']\").parent().parent().prev().children(1).click()");
            step.execute("$(\"span[text()=' node.server']\").click()");
            step.execute("$(\"div[text()='job.swgdRecv']\").parent().next().next().children(1).children(1).children(1).children(3).children(1).click()");
            step.closeWebDriver();
        }catch (ExecuteException e){
            throw new BugException(e.getMessage());
        }
    }

}