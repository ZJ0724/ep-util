package com.easipass.epUtil.entity;

import com.easipass.epUtil.annotation.Value;
import com.easipass.epUtil.config.ProjectConfig;
import com.easipass.epUtil.config.ResourcePathConfig;
import com.easipass.epUtil.exception.ConfigException;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.util.FileUtil;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Config {

    @Value("SWGD.url")
    private String sWGDUrl;

    @Value("SWGD.port")
    private int sWGDPort;

    @Value("SWGD.sid")
    private String sWGDSid;

    @Value("SWGD.username")
    private String sWGDUsername;

    @Value("SWGD.password")
    private String sWGDPassword;

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
    }

    /**
     * set,get
     * */
    public String getsWGDUrl() {
        return sWGDUrl;
    }

    public void setsWGDUrl(String sWGDUrl) {
        this.sWGDUrl = sWGDUrl;
    }

    public int getsWGDPort() {
        return sWGDPort;
    }

    public void setsWGDPort(int sWGDPort) {
        this.sWGDPort = sWGDPort;
    }

    public String getsWGDSid() {
        return sWGDSid;
    }

    public void setsWGDSid(String sWGDSid) {
        this.sWGDSid = sWGDSid;
    }

    public String getsWGDUsername() {
        return sWGDUsername;
    }

    public void setsWGDUsername(String sWGDUsername) {
        this.sWGDUsername = sWGDUsername;
    }

    public String getsWGDPassword() {
        return sWGDPassword;
    }

    public void setsWGDPassword(String sWGDPassword) {
        this.sWGDPassword = sWGDPassword;
    }

    public String getSftp83Url() {
        return sftp83Url;
    }

    public void setSftp83Url(String sftp83Url) {
        this.sftp83Url = sftp83Url;
    }

    public int getSftp83Port() {
        return sftp83Port;
    }

    public void setSftp83Port(int sftp83Port) {
        this.sftp83Port = sftp83Port;
    }

    public String getSftp83Username() {
        return sftp83Username;
    }

    public void setSftp83Username(String sftp83Username) {
        this.sftp83Username = sftp83Username;
    }

    public String getSftp83Password() {
        return sftp83Password;
    }

    public void setSftp83Password(String sftp83Password) {
        this.sftp83Password = sftp83Password;
    }

    public String getSftp83UploadPath() {
        return sftp83UploadPath;
    }

    public void setSftp83UploadPath(String sftp83UploadPath) {
        this.sftp83UploadPath = sftp83UploadPath;
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

    /**
     * 设置数据，通过key值对应字段设置数据
     *
     * @param args 所有参数
     * */
    public void setData(Map<String, Object> args) {
        // keys
        Set<String> keys = args.keySet();
        // thisClass
        Class<?> c = this.getClass();

        // 遍历keys
        for (String key : keys) {
            // value
            Object value = args.get(key);
            // key对应的字段
            Field field;
            try {
                field = c.getDeclaredField(key);
                field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                continue;
            }

            // 设置字段数据
            try {
                field.set(this, value);
            } catch (IllegalAccessException e) {
                throw ErrorException.getErrorException(e.getLocalizedMessage());
            }
        }

        // 同步到文件
        try {
            OutputStream outputStream = new FileOutputStream(ProjectConfig.CONFIG_FILE);
            outputStream.write(makeString().getBytes());
            outputStream.close();
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getLocalizedMessage());
        }
    }

    /**
     * 转成string
     * */
    public String makeString() {
        // 返回结果
        String result = "";
        // 所有字段
        Field[] fields = this.getClass().getDeclaredFields();

        // 遍历字段
        for (Field field : fields) {
            // 解锁字段
            field.setAccessible(true);
            // value注解
            Value value = field.getAnnotation(Value.class);

            // 只提取@value字段
            if (value == null) {
                continue;
            }

            // 字段名
            String fieldName = value.value();
            // 字段值
            String fieldValue;
            try {
                fieldValue = field.get(this) + "";
            } catch (IllegalAccessException e) {
                throw ErrorException.getErrorException(e.getLocalizedMessage());
            }

            result = result + fieldName + " = " + fieldValue + "\n";
        }

        return result.substring(0, result.length() - 1);
    }

}