package com.easipass.epUtil.api.websocket;

import com.easipass.epUtil.core.entity.CusFile;
import com.easipass.epUtil.core.entity.vo.CusFileComparisonMessageVo;
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
@ServerEndpoint(BaseWebsocketApi.URL + "cusFileComparison/{id}")
public class CusFileComparisonWebsocketApi extends BaseWebsocketApi {

    /**
     * 打开连接
     *
     * @param session session
     * */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        super.onOpen(session);

        LOG.info("id: " + id);
        CusFile cusFile = CusFile.getCusFile(id);

        if (cusFile == null) {
            sendMessage(CusFileComparisonMessageVo.getErrorType("报文未找到"));
            this.close();
            return;
        }

        cusFile.comparison(this);
    }

}