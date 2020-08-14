package com.easipass.util.core.http.SWGDSupply;

import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.exception.SupplySendException;

/**
 * 加签接口
 *
 * @author ZJ
 * */
public final class SignSWGDSupplyHttp extends AbstractSWGDSupplyHttp {

    /**
     * 构造函数
     *
     * @param sender 第三方代码
     */
    public SignSWGDSupplyHttp(String sender) {
        super("test/sign", "POST", sender);
    }

    /**
     * 获取sign
     *
     * @param data 数据
     *
     * @return sign
     * */
    public String getSign(String data) {
        // 私钥
        String privateKey = this.getSupplyUserFieldData("PRIVATE_KEY");

        if (privateKey == null) {
            throw new SupplySendException("第三方用户：" + this.sender + ", 不存在私钥");
        }

        JSONObject requestData = new JSONObject(true);

        requestData.put("privateKey", privateKey);
        requestData.put("data", this.parseData(data));

        // 响应
        String response = this.send(requestData.toJSONString());
        JSONObject responseJson = JSONObject.parseObject(response);

        if (!responseJson.get("flag").equals("T")) {
            throw new SupplySendException("加签失败");
        }

        return responseJson.get("data").toString();
    }

}