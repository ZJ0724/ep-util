package com.easipass.epUtil.entity.config;

import com.alibaba.fastjson.JSONObject;
import com.easipass.epUtil.exception.ConfigException;
import com.easipass.epUtil.exception.ErrorException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class AbstractConfig {

    /**
     * 加载数据
     * */
    public void loadData(JSONObject jsonObject) {
        if (jsonObject == null) {
            throw ConfigException.configFileException();
        }

        // 所有字段
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            String fieldName = field.getName();

            // 参数值为null，则配置异常
            Object value = jsonObject.get(fieldName);
            if (value == null) {
                throw ConfigException.configFileException();
            }

            try {
                field.set(this, value);
            } catch (IllegalAccessException e) {
                throw ErrorException.getErrorException(e.getMessage());
            }
        }
    }

}