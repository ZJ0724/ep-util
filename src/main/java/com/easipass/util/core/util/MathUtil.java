package com.easipass.util.core.util;

import java.text.NumberFormat;

/**
 * 数学工具
 *
 * @author ZJ
 * */
public final class MathUtil {

    /**
     * 计算百分比
     *
     * @param i i
     * @param j j
     *
     * @return 百分比
     * */
    public static String getPercentage(double i, double j) {
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(i / j);
    }

}