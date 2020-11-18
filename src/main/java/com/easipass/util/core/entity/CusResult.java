package com.easipass.util.core.entity;

/**
 * 回执
 *
 * @author ZJ
 * */
public interface CusResult {

    /**
     * 获取回执全内容
     *
     * @return 回执全内容
     * */
    String getData();

    /**
     * 获取回执名称
     *
     * @return 回执名称
     * */
    String getFileName();

}