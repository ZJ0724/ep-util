package com.easipass.util.api.websocket;

import com.easipass.util.core.DaKa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.List;

/**
 * 打卡websocket
 *
 * @author ZJ
 * */
@Component
@ServerEndpoint(BaseWebsocketApi.URL + "daKaLog")
public class DaKaLogWebsocketApi extends BaseWebsocketApi {

    private final String id = new Date().getTime() + "";

    /**
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(DaKaLogWebsocketApi.class);

    /**
     * 打卡
     * */
    private static final DaKa DA_KA = DaKa.getInstance();

    @Override
    @OnOpen
    public void onOpen(Session session) {
        super.onOpen(session);

        DA_KA.addDaKaLogWebsocket(this);
        List<String> logs = DA_KA.getLog();

        for (String log : logs) {
            this.sendMessage(log);
        }
    }

    /**
     * 监听连接关闭
     * */
    @OnClose
    public void onClose() {
        DA_KA.deleteDaKaLogWebsocket(this);
        log.info("{}已关闭", this.session.toString());
    }

    /**
     * 获取id
     *
     * @return id
     * */
    public String getId() {
        return this.id;
    }

}