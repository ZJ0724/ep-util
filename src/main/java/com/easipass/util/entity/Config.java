package com.easipass.util.entity;

import com.easipass.util.entity.DTO.AbstractDTO;
import com.easipass.util.entity.config.Key;
import com.easipass.util.exception.ConfigException;
import com.easipass.util.exception.ErrorException;
import com.easipass.util.util.ClassUtil;
import com.easipass.util.util.FileUtil;
import com.easipass.util.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
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
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(Config.class);

    /**
     * 构造函数
     *
     * @param resource 资源
     * */
    protected Config(Resource resource) {
        log.info("加载配置: {}", resource.getName());

        this.file = new File(ROOT_PATH + resource.getName());

        FileUtil.createFile(this.file, resource.getInputStream());
        resource.closeInputStream();

        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            throw new ErrorException(e.getMessage());
        }

        Properties properties = new Properties();

        try {
            properties.load(inputStreamReader);
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

                if (value == null) {
                    throw new ConfigException("配置文件出错");
                }

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

        log.info(this.toString());
        log.info("加载完成");
    }

    /**
     * 提交保存至文件
     * */
    protected final void commit() {
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

            if (fieldData == null) {
                fieldData = "";
            }

            if (field.getType() == List.class) {
                List<?> strings = (List<?>) fieldData;

                for (Object s : strings) {
                    value = StringUtil.append(value, ",", s.toString());
                }

                value = value.substring(1);
            } else {
                value = fieldData.toString();
            }

            data = StringUtil.append(data, key, " = ", value, "\n");
        }

        data = data.substring(0, data.length() - 1);

        FileUtil.setData(this.file, data);
    }

    /**
     * 通过DTO设置数据
     *
     * @param abstractDTO DTO
     * */
    public void setDataByDTO(AbstractDTO abstractDTO) {
        ClassUtil.assemblyData(abstractDTO, this);

        this.commit();
    }

}