package com.easipass.epUtil.entity;

import com.easipass.epUtil.entity.config.Key;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.util.FileUtil;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 配置
 *
 * @author ZJ
 * */
public class Config {

    /**
     * 配置文件存放路径
     * */
    protected static final String ROOT_PATH = Project.getInstance().getConfigPath() + "config/";

    /**
     * 所有带有key注解的字段
     * */
    private final List<Field> keyFields = new ArrayList<>();

    /**
     * 文件
     * */
    private final File file;

    /**
     * 构造函数
     *
     * @param path 路径
     * @param resource 资源
     * */
    protected Config(String path, Resource resource) {
        this.file = new File(ROOT_PATH + path);
        File par = this.file.getParentFile();

        if (par.exists()) {
            boolean is = par.mkdirs();
            if (!is) {
                throw new ErrorException("创建config文件夹失败");
            }
        }

        if (!this.file.exists()) {
            FileUtil.copyTextFile(resource.getInputStream(), this.file);
            resource.closeInputStream();
        }

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(this.file);
        } catch (FileNotFoundException e) {
            throw new ErrorException(e.getMessage());
        }

        Properties properties = new Properties();

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        // 所有字段
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Key.class)) {
                this.keyFields.add(field);
                field.setAccessible(true);

                // 字段名
                String name = field.getName();
                // 配置值
                String value = properties.getProperty(name);

                if (field.getType() == List.class) {
                    try {
                        field.set(this, Arrays.asList(value.split(",")));
                    } catch (IllegalAccessException e) {
                        throw new ErrorException(e.getMessage());
                    }
                    continue;
                }

                if (field.getType() == Integer.class) {
                    try {
                        field.set(this, Integer.parseInt(value));
                    } catch (IllegalAccessException e) {
                        throw new ErrorException(e.getMessage());
                    }
                    continue;
                }

                try {
                    field.set(this, value);
                } catch (IllegalAccessException e) {
                    throw new ErrorException(e.getMessage());
                }
            }
        }
    }

    /**
     * 保存
     * */
    public void save() {
        String data = "";

        for (Field field : this.keyFields) {
            Object fieldData;
            String key  = field.getName();
            String value = "";

            try {
                fieldData = field.get(this);
            } catch (IllegalAccessException e) {
                throw new ErrorException(e.getMessage());
            }

            if (field.getType() == List.class) {
                List<String> strings = (List<String>) fieldData;
                for (String s : strings) {
                    value = value + "," + s;
                }

            } else {
                value = fieldData.toString();
            }

            data = data + key + " = " + value + "\n";
        }

        FileUtil.setData(this.file, data);
    }

}