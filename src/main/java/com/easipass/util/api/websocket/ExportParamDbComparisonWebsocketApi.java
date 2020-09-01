package com.easipass.util.api.websocket;

import com.easipass.util.core.ParamDbComparator;
import com.easipass.util.core.Project;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.paramDbComparator.ExportParamDbComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.File;

/**
 * 参数库导出websocket
 *
 * @author ZJ
 * */
@Component
@ServerEndpoint(BaseWebsocketApi.URL + "exportParamDbComparison/{groupName}/{fileName}")
public class ExportParamDbComparisonWebsocketApi extends BaseWebsocketApi {

    /**
     * ParamDbComparator
     * */
    private ParamDbComparator paramDbComparator;

    /**
     * file
     * */
    private File file;

    /**
     * log
     * */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportParamDbComparisonWebsocketApi.class);

    /**
     * 建立连接
     *
     * @param session session
     * @param groupName 组名
     * @param fileName 文件名
     * */
    @OnOpen
    public void onOpen(Session session, @PathParam("groupName") String groupName, @PathParam("fileName") String fileName) {
        super.onOpen(session);

        try {
            this.paramDbComparator = new ExportParamDbComparator();
            this.file = new File(Project.CACHE_PATH, fileName);

            this.paramDbComparator.addWebsocket(this);
            this.paramDbComparator.comparison(groupName, this.file.getAbsolutePath());
        } finally {
            this.close();
        }
    }

    /**
     * 监听连接关闭
     * */
    @OnClose
    public void onClose() {
        LOGGER.info("ExportParamDbComparisonWebsocketApi已关闭");
        this.paramDbComparator.deleteWebsocket();
        if (!this.file.delete()) {
            throw new ErrorException("mdb文件删除失败");
        }
    }

}