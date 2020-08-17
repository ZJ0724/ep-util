package com.easipass.util.core;

import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.DTO.AbstractDTO;
import com.easipass.util.core.config.Key;
import com.easipass.util.core.util.ClassUtil;
import com.easipass.util.core.exception.ConfigException;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.util.FileUtil;
import com.easipass.util.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 抽象配置类
 *
 * @author ZJ
 * */
public abstract class Config {

    /**
     * 文件
     * */
    private final File file;

    /**
     * 所有带@Key注解的字段
     * */
    private final List<Field> fields;

    /**
     * log
     * */
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    /**
     * 构造函数
     *
     * @param resource 资源
     * */
    protected Config(Resource resource) {
        this.file = new File(resource.getPath());
        this.fields = new ArrayList<>();

        // 检查配置文件是否存在，不存在创建默认配置文件
        if (!this.file.exists()) {
            FileUtil.createFile(this.file);
        }

        // 所有字段
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Key.class)) {
                field.setAccessible(true);
                this.fields.add(field);
            }
        }

        setDefaultData();
        loadData(true);
        commit();
    }

    /**
     * 加载数据
     *
     * @param isInit 是否是初始化加载数据
     * */
    private void loadData(boolean isInit) {
        init();

        Properties properties = new Properties();

        try {
            FileInputStream fileInputStream = new FileInputStream(this.file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            properties.load(inputStreamReader);
            inputStreamReader.close();
            fileInputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        for (Field field : this.fields) {
            // 字段名
            String fieldName = field.getName();
            // 配置文件对应值
            Object data = properties.get(fieldName);

            if (data != null) {
                Class<?> fC = field.getType();

                try {
                    if (fC == List.class) {
                        field.set(this, StringUtil.parsList(data.toString()));
                    } else if (fC == Integer.class) {
                        field.set(this, Integer.parseInt(data.toString()));
                    } else {
                        field.set(this, data);
                    }
                } catch (IllegalAccessException e) {
                    throw new ConfigException(data + "数据格式错误");
                }
            } else {
                if (!isInit) {
                    throw new ErrorException("配置文件加载出错，请谨慎手动修改");
                }
            }
        }
    }

    /**
     * 加载数据
     * */
    public final void loadData() {
        this.loadData(false);
    }

    /**
     * 提交数据
     * */
    protected final void commit() {
        init();

        // 保存数据
        String commitData = "";

        for (Field field : this.fields) {
            // 字段名
            String fieldName = field.getName();
            // 字段值
            Object data;
            try {
                data = field.get(this);
            } catch (IllegalAccessException e) {
                throw new ErrorException(e.getMessage());
            }

            commitData = StringUtil.append(commitData, fieldName, " = ", data.toString(), "\n");
        }

        FileUtil.setData(this.file, commitData);

        LOGGER.info(this.toString());
    }

    @Override
    public final String toString() {
        JSONObject jsonObject = new JSONObject(true);

        for (Field field : this.fields) {
            // 字段名
            String fieldName = field.getName();
            // 字段值
            Object data;
            try {
                data = field.get(this);
            } catch (IllegalAccessException e) {
                throw new ErrorException(e.getMessage());
            }

            jsonObject.put(fieldName, data);
        }

        return jsonObject.toJSONString();
    }

    /**
     * 初始检查
     * */
    private void init() {
        if (!this.file.exists()) {
            throw new ErrorException("配置文件不存在，请不要手动删除配置文件");
        }
    }

    /**
     * 设置数据通过DTO
     *
     * @param abstractDTO abstractDTO
     * */
    public final void setData(AbstractDTO abstractDTO) {
        ClassUtil.assemblyData(abstractDTO, this);
        commit();
    }

    /**
     * 设置默认数据
     * */
    protected abstract void setDefaultData();

}
