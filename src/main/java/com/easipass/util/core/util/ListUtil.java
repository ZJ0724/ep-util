package com.easipass.util.core.util;

import com.easipass.util.core.exception.InfoException;
import java.util.List;

/**
 * ListUtil
 *
 * @author ZJ
 * */
public final class ListUtil {

    /**
     * 获取数据在集合中的位置
     *
     * @param list 数据集合
     * @param e 要查找的数据
     *
     * @return 要查找的数据在集合中的位置
     * */
    public static <E> int getIndex(List<E> list, E e) {
        if (e == null) {
            throw new InfoException("查找的数据为空");
        }
        for (int i = 0; i < list.size(); i++) {
            if (e.equals(list.get(i))) {
                return i;
            }
        }
        throw new InfoException("未找到数据");
    }

}