package com.easipass.util.api.websocket;

import com.easipass.util.core.ParamDbComparator;
import com.easipass.util.core.Project;
import com.easipass.util.core.paramDbComparator.ImportParamDbComparator;
import org.springframework.stereotype.Component;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.File;

/**
 * 参数库导入websocket
 *
 * @author ZJ
 * */
@Component
@ServerEndpoint(BaseWebsocketApi.URL + "importParamDbComparison/{groupName}/{fileName}")
public class ImportParamDbComparisonWebsocketApi extends BaseWebsocketApi {

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

        ParamDbComparator paramDbComparator = new ImportParamDbComparator();
        File file = new File(Project.CACHE_PATH, fileName);

        paramDbComparator.addWebsocket(this);
        paramDbComparator.comparison(groupName, file.getAbsolutePath());

        this.close();
    }

}