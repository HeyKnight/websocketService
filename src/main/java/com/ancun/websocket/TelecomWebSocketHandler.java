package com.ancun.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by Ace on 2017/1/13.
 */
public class TelecomWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private StringRedisTemplate template;

    private static Logger log = LoggerFactory.getLogger(TelecomWebSocketHandler.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received message: " + message.getPayload());
        ValueOperations<String, String> ops = template.opsForValue();
        String key = message.getPayload();
        if (! template.hasKey(key)) {
            ops.set(key, "websocket");
        }
        session.sendMessage(new TextMessage("Polo!"));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Hello websocket!");
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}
