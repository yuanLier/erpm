package edu.cqupt.mislab.erp.commons.config;

import edu.cqupt.mislab.erp.commons.websocket.CommonWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    @Autowired @Qualifier("commonWebSocketService")
    private CommonWebSocketService commonWebSocketService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){

        //注册比赛的websocket
        registry.addHandler(commonWebSocketService,"/commonWebSocket")
                .addInterceptors(commonWebSocketService)
                .setAllowedOrigins("*");

    }
}
