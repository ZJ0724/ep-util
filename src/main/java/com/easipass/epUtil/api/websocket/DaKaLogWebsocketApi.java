package com.easipass.epUtil.api.websocket;

import com.easipass.epUtil.entity.DaKa;
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
        LOG.info(this.session.toString() + "已关闭");
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