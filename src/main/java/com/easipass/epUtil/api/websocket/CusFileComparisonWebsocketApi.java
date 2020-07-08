package com.easipass.epUtil.api.websocket;

import com.alibaba.fastjson.JSONObject;
import com.easipass.epUtil.component.Log;
import com.easipass.epUtil.entity.CusFile;
import com.easipass.epUtil.exception.ErrorException;
import org.springframework.stereotype.Component;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * 报文比对websocket
 *
 * @author ZJ
 * */
@Component
@ServerEndpoint(BaseWebsocketApi.URL + "cusFileComparison/{id}")
public class CusFileComparisonWebsocketApi {

    /**
     * session
     * */
    private Session session;

    /**
     * id
     * */
    private String id;

    /**
     * 日志
     * */
    private static final Log log = Log.getLog();

    /**
     * 打开连接
     *
     * @param session session
     * */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        this.session = session;
        this.id = id;
        log.info("已连接: /websocket/cusFileComparison");
        log.info("session: " + session);
        log.info("id: " + this.id);

        this.comparison();
    }

    /**
     * 发送信息
     *
     * @param type 0: 标题信息；1: 比对信息
     * @param message 信息
     * @param note 比对的节点
     * @param result 比对结果
     * */
    private void sendMessage(String type, String message, String note, boolean result) {
        /*
         * {
         *     "type": "1",
         *     "message": "表头",
         *     "node": "",
         *     "result": ""
         * }
         * */
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.put("type", type);

        if ("1".equals(type)) {
            jsonObject.put("node", note);
            jsonObject.put("result", result);
        } else if ("0".equals(type)) {
            jsonObject.put("message", message);
        }

        try {
            this.session.getBasicRemote().sendText(jsonObject.toJSONString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 发送标题信息
     *
     * @param message 要发送的信息
     * */
    public void sendTitleMessage(String message) {
        this.sendMessage("0", message, null, false);
    }

    /**
     * 发送比对信息
     *
     * @param note 比对的节点
     * @param result 比对的结果
     * */
    public void sendComparisonMessage(String note, boolean result) {
        this.sendMessage("1", null, note, result);
    }

    /**
     * 关闭连接
     * */
    public void close() {
        try {
            this.session.close();
            log.info("已关闭session: " + this.session);
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }
    }

    /**
     * 比对
     * */
    private void comparison() {
        CusFile cusFile = CusFile.getCusFile(this.id);

        if (cusFile == null) {
            sendTitleMessage("报文未找到！");
            this.close();
            return;
        }

        cusFile.comparison(this.id, this);
    }

    /**
     * 监听异常
     *
     * @param throwable 异常
     * */
    @OnError
    public void onerror(Throwable throwable) {
        if (throwable.getClass() == ErrorException.class) {
            throw (ErrorException) throwable;
        }

        log.error("websocket监听异常: " + throwable.toString());
    }

}