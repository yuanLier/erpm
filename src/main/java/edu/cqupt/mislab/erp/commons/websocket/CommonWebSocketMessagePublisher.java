package edu.cqupt.mislab.erp.commons.websocket;

import org.springframework.web.socket.WebSocketMessage;

/**
 * @author chuyunfei
 * @description 
 * @date 20:44 2019/5/3
 **/
public interface CommonWebSocketMessagePublisher {

    /**
     * 向指定的比赛群体里面广播消息
     * @param gameId
     * @param message
     */
    void publish(Long gameId,WebSocketMessage<?> message);
}
