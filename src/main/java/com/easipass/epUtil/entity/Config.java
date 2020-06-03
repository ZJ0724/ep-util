package com.easipass.epUtil.entity;

import com.easipass.epUtil.annotation.Value;
import com.easipass.epUtil.config.ProjectConfig;
import com.easipass.epUtil.config.ResourcePathConfig;
import com.easipass.epUtil.exception.ConfigException;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.util.FileUtil;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Config {

    @Value("SWGD.url")
    private String SWGDUrl;

    @Value("SWGD.port")
    private int SWGDPort;

    @Value("SWGD.sid")
    private String SWGDSid;

    @Value("SWGD.username")
    private String SWGDUsername;

    @Value("SWGD.password")
    private String SWGDPassword;

    @Value("sftp83.url")
    private String sftp83Url;

    @Value("sftp83.port")
    private int sftp83Port;

    @Value("sftp83.username")
    private String sftp83Username;

    @Value("sftp83.password")
    private String sftp83Password;

    @Value("sftp83.uploadPath")
    private String sftp83UploadPath;

    /**
     * 单例
     * */
    private final static Config config = new Config();

    /**
     * 构造函数
     * */
    private Config() {
        // 检查配置文件是否存在，不存在创建默认配置文件
        if (!ProjectConfig.CONFIG_FILE.exists()) {
            // 不存在创建默认配置文件
            InputStream inputStream = Config.class.getResourceAsStream(ResourcePathConfig.CONFIG_PATH);
            FileUtil.copyTextFile(inputStream, ProjectConfig.CONFIG_FILE);
            try {
                inputStream.close();
            } catch (IOException e) {
                throw ErrorException.getErrorException(e.getMessage());
            }
        }

        this.loadData();
    }

    /**
     * set,get
     * */
    public String getSWGDUrl() {
        return SWGDUrl;
    }

    public int getSWGDPort() {
        return SWGDPort;
    }

    public String getSWGDSid() {
        return SWGDSid;
    }

    public String getSWGDUsername() {
        return SWGDUsername;
    }

    public String getSWGDPassword() {
        return SWGDPassword;
    }

    public String getSftp83Url() {
        return sftp83Url;
    }

    public int getSftp83Port() {
        return sftp83Port;
    }

    public String getSftp83Username() {
        return sftp83Username;
    }

    public String getSftp83Password() {
        return sftp83Password;
    }

    public String getSftp83UploadPath() {
        return sftp83UploadPath;
    }

    /**
     * 获取单例
     * */
    public static Config getConfig() {
        return config;
    }

    /**
     * 加载数据
     * */
    public void loadData() {
        // 配置文件Properties
        Properties configProperties = new Properties();
        // class
        Class<?> c = this.getClass();
        // 字段集合
        Field[] fields = c.getDeclaredFields();

        // 加载配置文件Properties
        try {
            FileReader fileReader = new FileReader(ProjectConfig.CONFIG_FILE);
            configProperties.load(fileReader);
            fileReader.close();
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }

        // 遍历字段
        for (Field field : fields) {
            // @value注解
            Value valueAnnotation = field.getAnnotation(Value.class);

            // 设置字段可编辑
            field.setAccessible(true);

            // 只有当被@Value标记的字段
            if (valueAnnotation == null) {
                continue;
            }

            // config数据
            String configValue = configProperties.getProperty(valueAnnotation.value());

            // 如果参数为null，则配置文件异常
            if (configValue == null) {
                throw ConfigException.configFileException();
            }

            // 加载字段
            try {
                if (field.getType().getSimpleName().equals("int")) {
                    field.setInt(this, Integer.parseInt(configValue));
                } else {
                    field.set(this, configValue);
                }
            } catch (IllegalAccessException e) {
                throw ErrorException.getErrorException("装载配置文件出错");
            }
        }
    }

    @Override
    public String toString() {
        return "Config{" +
                "SWGDUrl='" + SWGDUrl + '\'' +
                ", SWGDPort=" + SWGDPort +
                ", SWGDSid='" + SWGDSid + '\'' +
                ", SWGDUsername='" + SWGDUsername + '\'' +
                ", SWGDPassword='" + SWGDPassword + '\'' +
                ", sftp83Url='" + sftp83Url + '\'' +
                ", sftp83Port=" + sftp83Port +
                ", sftp83Username='" + sftp83Username + '\'' +
                ", sftp83Password='" + sftp83Password + '\'' +
                ", sftp83UploadPath='" + sftp83UploadPath + '\'' +
                '}';
    }

}