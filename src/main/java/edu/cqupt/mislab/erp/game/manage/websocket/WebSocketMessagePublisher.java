package edu.cqupt.mislab.erp.game.manage.websocket;

import org.springframework.web.socket.WebSocketMessage;

public interface WebSocketMessagePublisher {

    //向指定的比赛群体里面广播消息
    void publish(Long gameId,WebSocketMessage<?> message);
}
