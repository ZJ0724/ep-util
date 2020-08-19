package com.easipass.util.core.DTO.config;

import com.zj0724.util.springboot.parameterCheck.ErrorType;
import com.zj0724.util.springboot.parameterCheck.NotNull;

/**
 * ParamDbMappingDTO
 *
 * @author ZJ
 * */
public final class ParamDbMappingDTO {

    /**
     * 数据库表名
     * */
    @NotNull(value = "数据库表名不能为空", ERROR_TYPE = ErrorType.TIP)
    private String dbName;

    /**
     * mdb文件表名
     * */
    @NotNull(value = "mdb表名不能为空", ERROR_TYPE = ErrorType.TIP)
    private String mdbName;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getMdbName() {
        return mdbName;
    }

    public void setMdbName(String mdbName) {
        this.mdbName = mdbName;
    }

}