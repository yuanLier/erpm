package edu.cqupt.mislab.erp.commons.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cqupt.mislab.erp.commons.response.WebResponseUtil;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo.ResponseStatus;
import edu.cqupt.mislab.erp.game.manage.constant.ManageConstant;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
public class CommonWebSocketService extends AbstractWebSocketHandler implements HandshakeInterceptor, CommonWebSocketMessagePublisher {

    @Autowired
    protected GameBasicInfoRepository gameBasicInfoRepository;

    /*
    用于存储每一个比赛里面的session数据，每一个比赛的所有的session数据被存在同一个集合里面
     */
    protected final Map<Long,List<WebSocketSession>> webSocketSessionsMap = new ConcurrentHashMap<>();

    @Override//回显测试的作用
    public void handleMessage(WebSocketSession session,WebSocketMessage<?> message) throws Exception{
        //这里仅仅是对收到的数据进行显示和回显，主要是用于对websocket的测试，实际应用中这个websocket主要是用于后端主动给前端推送数据，不接受前端数据
        System.out.println(message.getPayload());
        session.sendMessage(message);
    }

    @Override//连接建立后通过比赛信息将该会话保存在对应的数据结构里面
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{

        //日志记录，便于调试
        log.info(session.getAttributes().get(ManageConstant.WEBSOCKET_REQUEST_ATTR_GAME_ID) + "连接建立成功！！(afterConnectionEstablished)");

        //获取连接时拦截下来的连接参数
        final Long gameId = (Long) session.getAttributes().get(ManageConstant.WEBSOCKET_REQUEST_ATTR_GAME_ID);

        if(webSocketSessionsMap.get(gameId) == null){

            //多个比赛的集合初始化是同步的
            synchronized(this){

                if(webSocketSessionsMap.get(gameId) == null){

                    List<WebSocketSession> webSocketSessionList = new ArrayList<>();

                    webSocketSessionList.add(session);

                    webSocketSessionsMap.put(gameId,webSocketSessionList);
                }
            }
        }else {

            //对集合的操作也是同步的
            synchronized(webSocketSessionsMap.get(gameId)){

                webSocketSessionsMap.get(gameId).add(session);
            }
        }
    }

    @Override//传输异常处理
    public void handleTransportError(WebSocketSession session,Throwable exception) throws Exception{

        //不打印日志都是耍流氓
        exception.printStackTrace();

        this.afterConnectionClosed(session,CloseStatus.SERVER_ERROR);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,CloseStatus status) throws Exception{

        //获取链接
        final Long gameId = (Long) session.getAttributes().get(ManageConstant.WEBSOCKET_REQUEST_ATTR_GAME_ID);

        webSocketSessionsMap.get(gameId).remove(session);

        //日志记录，便于调试
        log.warn(status.toString());
    }

    @Override//连接的拦截器，用于拦截需要的请求参数
    public boolean beforeHandshake(ServerHttpRequest request,ServerHttpResponse response,WebSocketHandler wsHandler,Map<String,Object> attributes) throws Exception{

        log.info("试图建立连接");

        try{

            //转换获取HttpServletRequest对象来获取请求参数
            final HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();

            //获取指定的请求参数
            final Long gameId = Long.valueOf(httpServletRequest.getParameter(ManageConstant.WEBSOCKET_REQUEST_ATTR_GAME_ID));

            //必须校验这个比赛必须存在
            if(!gameBasicInfoRepository.exists(gameId)){

                //获取响应对象
                final HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

                final WebResponseVo<Object> webResponseVo = WebResponseUtil.toFailResponseVoWithMessage(ResponseStatus.BAD_REQUEST,"没有携带正确的参数");

                //转换为json字符串
                final String responseMessage = new ObjectMapper().writeValueAsString(webResponseVo);

                //设置必要响应头并进行数据响应
                servletResponse.setContentType("application/json;charset=UTF-8");

                servletResponse.getWriter().print(responseMessage);

                servletResponse.flushBuffer();

                return false;
            }

            //记录连接参数
            attributes.put(ManageConstant.WEBSOCKET_REQUEST_ATTR_GAME_ID,gameId);

            log.info(gameId + "连接创建成功 (beforeHandshake)");

            return true;

        }catch(Exception e){

            e.printStackTrace();
        }

        log.info("连接创建失败");

        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,ServerHttpResponse response,WebSocketHandler wsHandler,Exception exception){ }

    @Override//用于外界想本数据集中发布数据
    public void publish(Long gameId,WebSocketMessage<?> message){

        //判断这个比赛是否存在
        if(webSocketSessionsMap.get(gameId) != null){

            //这里表示在比赛广播消息的时候是没办法同时增加会话进来的
            synchronized(webSocketSessionsMap.get(gameId)){

                final Iterator<WebSocketSession> iterator = webSocketSessionsMap.get(gameId).iterator();

                //迭代发送消息
                while(iterator.hasNext()){

                    final WebSocketSession webSocketSession = iterator.next();

                    //判断该会话是否还是开启的
                    if(webSocketSession.isOpen()){

                        try{
                            //向该回话发送消息
                            webSocketSession.sendMessage(message);
                        }catch(IOException e){

                            log.warn(gameId + "有一个websocket会话连接断开" + e.getMessage());
                            //移除这个出现异常的会话
                            iterator.remove();
                        }
                    }else {
                        //移除这个已经关闭的会话
                        iterator.remove();

                        log.warn(gameId + "移除一个已经断开了连接的websocket");
                    }
                }
            }
        }
    }
}
