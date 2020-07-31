package com.easipass.util.api.websocket;

import com.easipass.util.entity.CusMessage;
import com.easipass.util.entity.VO.CusMessageComparisonVO;
import org.springframework.stereotype.Component;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * 修撤单报文比对 websocket
 *
 * @author ZJ
 * */
@Component
@ServerEndpoint(BaseWebsocketApi.URL + "decModCusMessageComparison/{id}")
public class DecModCusMessageComparisonWebsocketApi extends BaseWebsocketApi {

    /**
     * 打开连接
     *
     * @param session session
     * */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        super.onOpen(session);

        LOG.info("id: " + id);
        CusMessage cusMessage = CusMessage.get(id);

        if (cusMessage == null) {
            this.sendMessage(CusMessageComparisonVO.getErrorType("报文未找到"));
            this.close();
            return;
        }

        cusMessage.comparison(this);

        cusMessage.delete();
    }

}