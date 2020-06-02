package com.easipass.epUtil.annotation;

import com.easipass.epUtil.exception.ErrorException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Properties;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

    String value();

    class Method {
        /**
         * 装载配置
         * */
        public static boolean load(Properties properties, Class<?> c) {
            Config config = c.getAnnotation(Config.class);
            String value = config.value();

            // 遍历字段
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                // 配置文件参数
                String configValue = properties.getProperty(value + "." + field.getName());
                if (configValue == null) {
                    return false;
                }
                try {
                    if (field.getType().getSimpleName().equals("int")) {
                        field.setInt(null, Integer.parseInt(configValue));
                    } else {
                        field.set(null, configValue);
                    }
                } catch (IllegalAccessException e) {
                    throw ErrorException.getErrorException("装载配置文件出错");
                }
            }

            return true;
        }
    }

}
