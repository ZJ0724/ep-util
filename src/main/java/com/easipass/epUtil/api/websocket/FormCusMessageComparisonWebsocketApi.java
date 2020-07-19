package com.easipass.epUtil.api.websocket;

import com.easipass.epUtil.entity.VO.CusFileComparisonMessageVO;
import com.easipass.epUtil.entity.cusMessage.FormCusMessage;
import org.springframework.stereotype.Component;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * 报文比对websocket
 *
 * @author ZJ
 * */
@Component
@ServerEndpoint(BaseWebsocketApi.URL + "formCusMessageComparison/{id}")
public class FormCusMessageComparisonWebsocketApi extends BaseWebsocketApi {

    /**
     * 打开连接
     *
     * @param session session
     * */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        super.onOpen(session);

        LOG.info("id: " + id);
        FormCusMessage formCusMessage = FormCusMessage.getFormCusMessage(id);

        if (formCusMessage == null) {
            this.sendMessage(CusFileComparisonMessageVO.getErrorType("报文未找到"));
            this.close();
            return;
        }

        formCusMessage.comparison(this);
    }

}