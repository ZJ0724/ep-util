package com.easipass.epUtil.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 开启websocket支持
 *
 * @author ZJ
 * */
@Configuration
public class WebSocketHandler {

    /**
     * 开启websocket支持
     * */
    @Bean
    public ServerEndpointExporter websocket() {
        return new ServerEndpointExporter();
    }

}