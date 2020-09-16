package com.easipass.util.core.service;

import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.entity.FormCusMessage;
import com.easipass.util.core.exception.ComparisonException;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

/**
 * 报文服务
 * */
@Service
public final class CusMessageService {

    /**
     * 比对报关单报文
     *
     * @param multipartFile multipartFile
     * */
    public ComparisonMessage formComparison(MultipartFile multipartFile) {
        // 报关单报文
        FormCusMessage formCusMessage = new FormCusMessage(multipartFile);
        // 比对信息
        ComparisonMessage result = new ComparisonMessage();

        // 数据库表头
        List<JSONObject> formHeadList = SWGDDatabase.queryFormHeadToJson(formCusMessage.getEdiNo());
        if (formHeadList.size() != 1) {
            throw new ComparisonException("数据库表头未找到数据(ediNo: " + formCusMessage.getEdiNo() + ")");
        }
        JSONObject formHead = formHeadList.get(0);

        // 比对<DecSign>
        Element DecSign = formCusMessage.getDecSign();

        return result;
    }

    /**
     * 比对信息
     *
     * @author ZJ
     * */
    public static final class ComparisonMessage {

        /**
         * 状态
         * */
        private boolean flag;

        /**
         * 信息
         * */
        private final List<String> messages = new ArrayList<>();

        /**
         * setFlag
         *
         * @param flag flag
         * */
        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        /**
         * addMessage
         *
         * @param message message
         * */
        public void addMessage(String message) {
            this.messages.add(message);
        }

        @Override
        public String toString() {
            JSONObject jsonObject = new JSONObject(true);
            jsonObject.put("flag", this.flag);
            jsonObject.put("messages", this.messages);
            return jsonObject.toJSONString();
        }

    }

}