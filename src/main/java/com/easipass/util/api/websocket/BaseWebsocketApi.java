package com.easipass.util.api.websocket;

import com.easipass.util.exception.ErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    protected Session session;

    /**
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(BaseWebsocketApi.class);

    public void onOpen(Session session) {
        this.session = session;
        log.info("已连接session: {}", this.session);
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
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 关闭连接
     * */
    public final void close() {
        try {
            this.session.close();
            log.info("已关闭session: " + this.session);
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
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

        log.error("websocket监听异常: ", throwable);
    }

}