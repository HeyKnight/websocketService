package com.ancun.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by Ace on 2017/1/13.
 */
public class TelecomWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private RedisTemplate template;

    private static Logger log = LoggerFactory.getLogger(TelecomWebSocketHandler.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received message: " + message.getPayload());
        System.out.println(session.getId() + "连接");
        String key = message.getPayload();

        template.opsForValue().set("websocketSessionId:" + session.getId(), key);
        template.opsForValue().set("websocketUserInfo:" + key, key);
        if (! template.hasKey("websocketCount:" + key))
            template.opsForValue().set("websocketCount:" + key, 1);
        else
            template.opsForValue().increment("websocketCount:" + key, 1L);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Hello websocket!");
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(session.getId() + "关闭连接");
        String userTel = template.opsForValue().get("websocketSessionId:" + session.getId()).toString();
        template.delete("websocketSessionId:" + session.getId());
        template.opsForValue().increment("websocketCount:" + userTel, -1L);
            if (!template.hasKey("websocketSessionId:" + session.getId())) {
                template.delete("websocketUserInfo:" + userTel);
                template.delete("websocketCount:" + userTel);
            }
//        }
    }


}
