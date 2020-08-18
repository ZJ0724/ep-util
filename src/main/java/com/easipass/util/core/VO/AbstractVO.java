package com.easipass.util.core.VO;

import com.easipass.util.core.util.ClassUtil;

/**
 * 抽象VO类
 *
 * @author ZJ
 * */
public abstract class AbstractVO {

    /**
     * 设置数据
     *
     * @param data 数据
     * */
    public final void setData(Object data) {
        ClassUtil.assemblyData(data, this);
    }

}