package edu.cqupt.mislab.erp.game.compete.init;

import edu.cqupt.mislab.erp.game.manage.constant.ManageConstant;
import edu.cqupt.mislab.erp.game.manage.websocket.WebSocketMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

@Service
public class GameCompeteInitService {

    @Autowired
    private WebSocketMessagePublisher webSocketMessagePublisher;

    /**
     * 初始化一场比赛
     * @param gameId
     */
    public void gameInit(Long gameId){

        System.out.println(this.getClass().getSimpleName());

        //发布一个比赛初始化完成的信息
        webSocketMessagePublisher.publish(gameId,new TextMessage(ManageConstant.GAME_INIT_COMPLETE + gameId));
    }
}
