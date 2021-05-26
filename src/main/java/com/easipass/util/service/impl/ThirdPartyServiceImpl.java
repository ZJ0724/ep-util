package com.easipass.util.service.impl;

import com.easipass.util.service.ThirdPartyService;
import com.easipass.util.util.SWGDDatabaseUtil;
import com.zj0724.common.component.Http;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.JsonUtil;
import com.zj0724.common.util.MapUtil;
import com.zj0724.common.util.StringUtil;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public final class ThirdPartyServiceImpl implements ThirdPartyService {

    @Override
    public String send(String userCode, String url, String requestData) {
        // 参数校验
        if (StringUtil.isEmpty(userCode)) {
            throw new InfoException("用户代码不能为空");
        }
        if (StringUtil.isEmpty(url)) {
            throw new InfoException("请求地址不能为空");
        }
        if (StringUtil.isEmpty(requestData)) {
            throw new InfoException("请求数据不能为空");
        }
        Map<?, ?> requestDataMap = JsonUtil.parseObject(requestData, Map.class);

        // 获取用户信息
        Map<String, Object> user = SWGDDatabaseUtil.queryOne("SELECT * FROM SWGD.T_SWGD_SUPPLY_USER WHERE SENDER = '" + userCode + "'");
        if (user == null) {
            throw new InfoException(userCode + "不存在");
        }
        System.out.println(user);

        // password
        String password = MapUtil.getValue(user, "PASSWORD", String.class);
        // publicKey
        String publicKey = MapUtil.getValue(user, "PUBLIC_KEY", String.class);
        // privateKey
        String privateKey = MapUtil.getValue(user, "PRIVATE_KEY", String.class);
        if (StringUtil.isEmpty(password)) {
            throw new InfoException("password is null");
        }
        if (StringUtil.isEmpty(publicKey)) {
            throw new InfoException("publicKey is null");
        }
        if (StringUtil.isEmpty(privateKey)) {
            throw new InfoException("privateKey is null");
        }

        // 加密
        Http http = new Http("http://192.168.120.83:9091/swgdsupplyspboot/test/aes", Http.Type.POST);
        http.setContentTypeByJson();
        Map<String, Object> map = new HashMap<>();
        map.put("password", password);
        map.put("data", requestDataMap);
        String aesData = http.send(JsonUtil.toJsonString(map));
        Map<?, ?> aesDataMap = JsonUtil.parseObject(aesData, Map.class);
        if (!"T".equals(MapUtil.getValue(aesDataMap, "flag", String.class))) {
            throw new InfoException("加密失败：" + aesData);
        }
        String aes = MapUtil.getValue(aesDataMap, "data", String.class);

        // 加签
        Http http2 = new Http("http://192.168.120.83:9091/swgdsupplyspboot/test/sign", Http.Type.POST);
        http2.setContentTypeByJson();
        Map<String, Object> map2 = new HashMap<>();
        map2.put("privateKey", privateKey);
        map2.put("data", requestDataMap);
        String signData = http2.send(JsonUtil.toJsonString(map2));
        Map<?, ?> signDataMap = JsonUtil.parseObject(signData, Map.class);
        if (!"T".equals(MapUtil.getValue(signDataMap, "flag", String.class))) {
            throw new InfoException("加签失败：" + signData);
        }
        String sign = MapUtil.getValue(signDataMap, "data", String.class);

        // 调用接口
        Http http1 = new Http(url, Http.Type.POST);
        http1.setContentTypeByJson();
        http1.addHeader("X-SWGD-Receiver", "SWGD");
        http1.addHeader("X-SWGD-PublicKey", publicKey);
        http1.addHeader("X-SWGD-Sender", userCode);
        http1.addHeader("X-SWGD-Sign", sign);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("data", aes);
        return http1.send(JsonUtil.toJsonString(map1));
    }

    @Override
    public List<String> getUsers() {
        List<Map<String, Object>> query = SWGDDatabaseUtil.query("SELECT * FROM SWGD.T_SWGD_SUPPLY_USER");
        List<String> result = new ArrayList<>();
        for (Map<String, Object> map : query) {
            result.add(MapUtil.getValue(map, "SENDER", String.class));
        }
        return result;
    }

}