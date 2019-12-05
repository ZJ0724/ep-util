package com.easipass.EP_Util_Server.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "chromedriver")
public class ChromeDriver {

    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static String getChromeDriverPath(String version){
        return "/chromedriver/"+version+"/chromedriver.exe";
    }

}