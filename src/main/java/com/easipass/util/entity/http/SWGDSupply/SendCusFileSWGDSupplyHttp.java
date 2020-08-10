package com.easipass.util.entity.http.SWGDSupply;

import com.alibaba.fastjson.JSONObject;
import com.easipass.util.exception.SupplySendException;

/**
 * 发送报文接口
 *
 * @author ZJ
 * */
public final class SendCusFileSWGDSupplyHttp extends AbstractSWGDSupplyHttp {

    /**
     * 构造函数
     *
     * @param sender 第三方代码
     */
    public SendCusFileSWGDSupplyHttp(String sender) {
        super("cusFileSend/sendCusFile", "POST", sender);
    }

    /**
     * 发送报文
     *
     * @param data 报文数据
     * */
    public void sendCusFile(String data) {
        // 加签
        String sign = new SignSWGDSupplyHttp(this.sender).getSign(data);
        // 加密
        String aesData = new AesSWGDSupplyHttp(this.sender).aes(data);
        // 公钥
        String publicKey = this.getSupplyUserFieldData("PUBLIC_KEY");

        if (publicKey == null) {
            throw new SupplySendException("第三方用户: " + this.sender + "公钥不存在");
        }

        // 设置请求头
        this.setHeader("X-SWGD-Receiver", "SWGD");
        this.setHeader("X-SWGD-PublicKey", publicKey);
        this.setHeader("X-SWGD-Sign", sign);
        this.setHeader("X-SWGD-Zipflag", "0");
        this.setHeader("X-SWGD-Sender", this.sender);

        // 请求数据
        JSONObject requestJson = new JSONObject(true);

        requestJson.put("data", aesData);

        // 响应数据
        String response = this.send(requestJson.toJSONString());
        // 响应数据json
        JSONObject responseJson = JSONObject.parseObject(response);

        if (!responseJson.getString("flag").equals("T")) {
            throw new SupplySendException("发送报文失败: " + response);
        }
    }

}