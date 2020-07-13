package com.easipass.epUtil.api.websocket;

import com.easipass.epUtil.component.Log;
import com.easipass.epUtil.exception.ErrorException;
import javax.websocket.OnError;
import javax.websocket.Session;
import java.io.IOException;

/**
 * 基础websocket
 *
 * @author ZJ
 * */
public class BaseWebsocketApi {

    /**
     * 前置URL
     * */
    protected static final String URL = "/websocket/";

    /**
     * session
     * */
    private Session session;

    /**
     * 日志
     * */
    protected static final Log LOG = Log.getLog();

    public void onOpen(Session session) {
        this.session = session;
        LOG.info("已连接session: " + this.session);
    }

    /**
     * 发送消息
     *
     * @param message 消息
     * */
    public final void sendMessage(Object message) {
        try {
            this.session.getBasicRemote().sendText(message.toString());
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * 关闭连接
     * */
    public final void close() {
        try {
            this.session.close();
            LOG.info("已关闭session: " + this.session);
        } catch (IOException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }
    }

    /**
     * 监听异常
     *
     * @param throwable 异常
     * */
    @OnError
    public final void onerror(Throwable throwable) {
        // 异常类
        Class<?> errorClass = throwable.getClass();

        if (errorClass == ErrorException.class) {
            throw (ErrorException) throwable;
        }
        if (errorClass == NullPointerException.class) {
            throw (NullPointerException) throwable;
        }

        LOG.error("websocket监听异常: " + throwable.getMessage());
    }

}