package edu.cqupt.mislab.erp.game.manage.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cqupt.mislab.erp.commons.response.ResponseUtil;
import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.game.manage.constant.ManageConstant;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.*;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class GameManageWebsocket extends AbstractWebSocketHandler implements HandshakeInterceptor, WebSocketMessagePublisher {

    @Autowired
    private GameBasicInfoRepository gameBasicInfoRepository;

    private static final Map<Long,List<WebSocketSession>> webSocketSessionsMap = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session,TextMessage message) throws Exception{

        System.out.println(message.getPayload());

        session.sendMessage(message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{

        log.info("连接建立成功！！");

        final Long gameId = (Long) session.getAttributes().get(ManageConstant.WEBSOCKET_REQUEST_ATTR_GAME_ID);

        if(webSocketSessionsMap.get(gameId) == null){

            synchronized(this){

                if(webSocketSessionsMap.get(gameId) == null){

                    List<WebSocketSession> webSocketSessionList = new ArrayList<>();

                    webSocketSessionList.add(session);

                    webSocketSessionsMap.put(gameId,webSocketSessionList);
                }
            }
        }else {

            synchronized(webSocketSessionsMap.get(gameId)){

                webSocketSessionsMap.get(gameId).add(session);
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session,Throwable exception) throws Exception{

        exception.printStackTrace();

        this.afterConnectionClosed(session,CloseStatus.SERVER_ERROR);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,CloseStatus status) throws Exception{

        log.warn(status.toString());
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,ServerHttpResponse response,WebSocketHandler wsHandler,Map<String,Object> attributes) throws Exception{

        log.info("试图建立连接");

        try{

            final HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();

            final Long gameId = Long.valueOf(httpServletRequest.getParameter(ManageConstant.WEBSOCKET_REQUEST_ATTR_GAME_ID));

            if(!gameBasicInfoRepository.exists(gameId)){

                final HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

                final ResponseVo<Object> responseVo = ResponseUtil.toFailResponseVo(HttpStatus.BAD_REQUEST,"没有携带正确的参数");

                final String responseMessage = new ObjectMapper().writeValueAsString(responseVo);

                servletResponse.setContentType("application/json;charset=UTF-8");

                servletResponse.getWriter().print(responseMessage);

                servletResponse.flushBuffer();

                return false;
            }

            //记录连接参数
            attributes.put(ManageConstant.WEBSOCKET_REQUEST_ATTR_GAME_ID,gameId);

            System.out.println(gameId);

            log.info("连接创建成功");

            return true;

        }catch(Exception e){

            e.printStackTrace();
        }

        log.info("连接创建失败");

        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,ServerHttpResponse response,WebSocketHandler wsHandler,Exception exception){ }

    @Override
    public void publish(Long gameId,WebSocketMessage<?> message){

        final List<WebSocketSession> webSocketSessions = webSocketSessionsMap.get(gameId);

        if(webSocketSessions != null){

            final Iterator<WebSocketSession> iterator = webSocketSessions.iterator();

            while(iterator.hasNext()){

                final WebSocketSession webSocketSession = iterator.next();

                if(webSocketSession.isOpen()){

                    try{

                        webSocketSession.sendMessage(message);
                    }catch(IOException e){

                        log.warn("有一个websocket会话连接断开" + e.getMessage());
                        iterator.remove();
                    }
                }else {

                    iterator.remove();
                    log.warn("移除一个已经断开了连接的websocket");
                }
            }
        }
    }
}
