package com.ancun.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ace on 2017/1/13.
 */
public class TelecomWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private RedisTemplate template;

    private static Logger log = LoggerFactory.getLogger(TelecomWebSocketHandler.class);

    private static String userTel;

    private static String phpSessId;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received message: " + message.getPayload());
        System.out.println(session.getId() + "连接");
        String messagePayload = message.getPayload();

        String[] messages = messagePayload.split("_");
        // php sessionId
        phpSessId = messages[0];
        userTel = messages[1];

        // 防止已登陆浏览器未退出登录关闭后重新进去时的重复连接
        if (template.hasKey("websocketUserInfo:" + userTel) &&
                !template.opsForValue().get("websocketUser:" + userTel).equals(phpSessId))
            session.close();
        else {
            template.opsForValue().set("websocketUser:" + userTel, phpSessId);
            // 设置失效时间
            template.expire("websocketUser:" + userTel, 60 * 15 - 1, TimeUnit.SECONDS);
            // 添加websocket连接sessionId
            template.opsForSet().add("websocketUserInfo:" + userTel, session.getId());
            // 设置失效时间
            template.expire("websocketUserInfo:" + userTel, 60 * 15 - 1, TimeUnit.SECONDS);
        }

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Hello websocket!");
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(session.getId() + "关闭连接");
        // 删除断开连接的websocket相关信息
        if (template.hasKey("websocketUserInfo:" + userTel))
            template.opsForSet().remove("websocketUserInfo:" + userTel, session.getId());
        if (template.opsForSet().size("websocketUserInfo:" + userTel) == 0)
            template.delete("websocketUser:" + userTel);
    }

}
