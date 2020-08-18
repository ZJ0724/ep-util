package com.easipass.util.core.util;

import com.easipass.util.core.exception.ErrorException;
import java.lang.reflect.Field;

/**
 * class工具
 *
 * @author ZJ
 * */
public final class ClassUtil {

    /**
     * 获取字段的值
     *
     * @param o 要获取值的对象
     * @param fieldName 字段名
     *
     * @return 字段值
     * */
    public static Object getFieldValue(Object o, String fieldName) {
        Object result;

        try {
            Field field = o.getClass().getDeclaredField(fieldName);

            field.setAccessible(true);
            result = field.get(o);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            result = null;
        }

        return result;
    }

    /**
     * 装配数据，通过遍历第一个对象字段，装配至第二个对象
     *
     * @param o1 第一个对象
     * @param o2 第二个对象
     * */
    public static void assemblyData(Object o1, Object o2) {
        // o1所有字段
        Field[] fields = o1.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            // 字段名
            String fieldName = field.getName();
            // o2对应字段
            Field o2Field;
            try {
                o2Field = o2.getClass().getDeclaredField(fieldName);
                o2Field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                continue;
            }

            // o1字段值
            Object fieldValue = getFieldValue(o1, fieldName);

            try {
                o2Field.set(o2, fieldValue);
            } catch (IllegalAccessException e) {
                throw new ErrorException(e.getMessage());
            }
        }
    }

}