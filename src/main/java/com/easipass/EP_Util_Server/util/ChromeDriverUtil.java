package com.easipass.EP_Util_Server.util;

import java.io.File;

public class ChromeDriverUtil {

    /**
     * 获取谷歌驱动文件
     * */
    public static File getChromeDriverFile() {
        return new File(System.getProperty("user.dir"), "/chromeDriver/chromedriver.exe");
    }

}
