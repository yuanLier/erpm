package edu.cqupt.mislab.erp.game.compete.init;

import edu.cqupt.mislab.erp.commons.websocket.CommonWebSocketMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameCompeteInitService {

    @Autowired
    private CommonWebSocketMessagePublisher webSocketMessagePublisher;

    /**
     * 初始化一场比赛
     * @param gameId
     */
    public boolean gameInit(Long gameId){

        System.out.println("gameInit");

        return true;
    }
}
