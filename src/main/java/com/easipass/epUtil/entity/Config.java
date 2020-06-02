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

    /**
     * 配置文件数据
     * */
    private static final Properties data = new Properties();

    static {
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

        // 加载配置文件
        try {
            FileReader fileReader = new FileReader(ProjectConfig.CONFIG_FILE);
            data.load(fileReader);
            fileReader.close();
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }
    }

    protected Config() {}

    /**
     * 加载
     * */
    public void load() {
        // class
        Class<?> c = this.getClass();
        // @Config上的值
        String configAnnotationValue = c.getAnnotation(com.easipass.epUtil.annotation.Config.class).value();
        // 字段集合
        Field[] fields = c.getDeclaredFields();

        // 遍历字段
        for (Field field : fields) {
            // 只有当被@Value标记的字段
            if (!field.isAnnotationPresent(Value.class)) {
                continue;
            }

            // 配置文件参数
            String configValue = data.getProperty(configAnnotationValue + "." + field.getName());

            // 如果参数为null，则配置文件异常
            if (configValue == null) {
                throw ConfigException.configFileException();
            }

            // 加载字段
            field.setAccessible(true);
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

}