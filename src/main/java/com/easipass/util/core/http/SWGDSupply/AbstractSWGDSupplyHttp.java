package com.easipass.util.core.http.SWGDSupply;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.Http;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.SupplySendException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AbstractSWGDSupplyHttp
 *
 * @author ZJ
 * */
public abstract class AbstractSWGDSupplyHttp extends Http {

    /**
     * url
     * */
    private static final String URL = "http://192.168.120.83:9091/swgdsupplyspboot";

    /**
     * 第三方代码
     * */
    protected String sender;

    /**
     * 构造函数
     *
     * @param url  url
     * @param type type
     * @param sender 第三方代码
     */
    protected AbstractSWGDSupplyHttp(String url, String type, String sender) {
        super(URL + "/" + url, type);
        this.sender = sender;
    }

    /**
     * 获取用户字段信息
     *
     * @param filedName 字段名
     *
     * @return 字段值
     * */
    protected final String getSupplyUserFieldData(String filedName) {
        // 数据库第三方用户信息
        SWGDDatabase swgdDatabase = new SWGDDatabase();
        ResultSet resultSet = swgdDatabase.querySupplyUser(this.sender);

        try {
            if (!resultSet.next()) {
                throw new SupplySendException("不存在第三方用户：" + this.sender);
            }
            return SWGDDatabase.getFiledData(resultSet, filedName);
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdDatabase.close();
        }
    }

    /**
     * 解析data
     *
     * @param data 数据
     *
     * @return JSONObject
     * */
    protected final JSONObject parseData(String data) {
        try {
            return JSONObject.parseObject(data);
        } catch (JSONException e) {
            throw new SupplySendException("报文数据解析失败");
        }
    }

}