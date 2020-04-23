package com.easipass.EpUtilServer.service;

import com.easipass.EpUtilServer.config.Sftp83Config;
import com.easipass.EpUtilServer.entity.Sftp;
import com.easipass.EpUtilServer.util.EPMSUtil;
import com.zj0724.uiAuto.WebDriver;
import org.dom4j.Document;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;

@Service
public class BaseService {

    /**
     * sftp
     * */
    public static final ThreadLocal<Sftp> SFTP_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * webdriver
     * */
    public static final ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 上传回执
     * */
    public static void uploadResult(Document document, String fileName) {
        SFTP_THREAD_LOCAL.get().uploadFile(Sftp83Config.uploadPath, fileName, new ByteArrayInputStream(document.asXML().getBytes()));
        EPMSUtil.run(WEB_DRIVER_THREAD_LOCAL.get());
    }

}
