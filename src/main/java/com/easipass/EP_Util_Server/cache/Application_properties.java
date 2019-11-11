package com.easipass.EP_Util_Server.cache;

import com.easipass.EP_Util_Server.exception.BugException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Application_properties {

    public static String uploadPath=null;
    public static String chromeDriverVersion=null;
    public static String SWGD_url=null;
    public static int SWGD_port=0;
    public static String SWGD_sid=null;
    public static String SWGD_username=null;
    public static String SWGD_password=null;
    public static String sftp_83_url=null;
    public static int sftp_83_port=0;
    public static String sftp_83_username=null;
    public static String sftp_83_password=null;

    static {

        try {
            File application_propertiesFile=new File(System.getProperty("user.dir"),"application.properties");
            InputStream application_propertiesInputStream=new FileInputStream(application_propertiesFile);
            Properties application_properties=new Properties();
            application_properties.load(application_propertiesInputStream);
            uploadPath=application_properties.getProperty("uploadPath");
            chromeDriverVersion=application_properties.getProperty("chromeDriverVersion");
            SWGD_url=application_properties.getProperty("SWGD.url");
            SWGD_port=Integer.parseInt(application_properties.getProperty("SWGD.port"));
            SWGD_sid=application_properties.getProperty("SWGD.sid");
            SWGD_username=application_properties.getProperty("SWGD.username");
            SWGD_password=application_properties.getProperty("SWGD.password");
            sftp_83_url=application_properties.getProperty("sftp.83.url");
            sftp_83_port=Integer.parseInt(application_properties.getProperty("sftp.83.port"));
            sftp_83_username=application_properties.getProperty("sftp.83.username");
            sftp_83_password=application_properties.getProperty("sftp.83.password");
        }catch (IOException e){
            throw new BugException("配置文件未找到");
        }

    }

}
