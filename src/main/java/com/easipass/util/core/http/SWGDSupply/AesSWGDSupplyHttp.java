package com.easipass.util.core.http.SWGDSupply;

import com.alibaba.fastjson.JSONObject;
import com.easipass.util.exception.SupplySendException;

/**
 * AES 加密接口
 *
 * @author ZJ
 * */
public final class AesSWGDSupplyHttp extends AbstractSWGDSupplyHttp {

    /**
     * 构造函数
     *
     * @param sender 第三方代码
     */
    public AesSWGDSupplyHttp(String sender) {
        super("test/aes", "POST", sender);
    }

    /**
     * 加密
     *
     * @param data 要加密的数据
     *
     * @return 加密后的数据
     * */
    public String aes(String data) {
        // 密码
        String password = this.getSupplyUserFieldData("PASSWORD");

        if (password == null) {
            throw new SupplySendException("第三方用户: " + this.sender + "密码不存在");
        }

        // 请求数据
        JSONObject requestJson = new JSONObject(true);

        requestJson.put("password", password);
        requestJson.put("data", this.parseData(data));

        // 响应数据
        String responseData = this.send(requestJson.toJSONString());
        JSONObject responseJson = JSONObject.parseObject(responseData);

        if (!responseJson.get("flag").equals("T")) {
            throw new SupplySendException("加密失败");
        }

        return responseJson.getString("data");
    }

}