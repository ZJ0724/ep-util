package com.easipass.util.entity.http.SWGDSupply;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.easipass.util.entity.Http;
import com.easipass.util.entity.oracle.SWGDOracle;
import com.easipass.util.exception.SupplySendException;
import java.sql.ResultSet;

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
        SWGDOracle swgdOracle = new SWGDOracle();

        swgdOracle.connect();

        // 数据库第三方用户信息
        ResultSet resultSet = swgdOracle.querySupplyUser(this.sender);

        if (resultSet == null) {
            swgdOracle.close();
            throw new SupplySendException("不存在第三方用户：" + this.sender);
        }

        String result = SWGDOracle.getFiledData(resultSet, filedName);

        swgdOracle.close();

        return result;
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