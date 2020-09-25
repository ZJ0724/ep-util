package com.easipass.util.core.service;

import com.easipass.util.core.BaseException;
import com.easipass.util.core.entity.ErrorLog;

/**
 * 系统服务
 *
 * @author ZJ
 * */
public final class SystemService {

    /**
     * 获取错误日志内容
     *
     * @return 错误日志内容
     * */
    public String getErrorLog() {
        String result = ErrorLog.getErrorLog().getData();
        return result == null ? "" : result;
    }

    /**
     * 清空错误日志
     *
     * @param key 口令
     * */
    public void cleanErrorLog(String key) {
        if ("ZJ".equals(key)) {
            ErrorLog.getErrorLog().setData("");
        } else {
            throw new BaseException("口令错误") {};
        }
    }

}