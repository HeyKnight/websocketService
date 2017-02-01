package com.ancun.config;

import com.ancun.websocket.TelecomWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * websocket配置类
 *
 * @author jarvan [NO.0051]
 * @since 2017年01月19日 10:38
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(telecomWebSocketHandler(), "/websocket")
                .setAllowedOrigins("*")    // 设置访问来源
                .withSockJS();
    }

    @Bean
    public WebSocketHandler telecomWebSocketHandler() {
        return new TelecomWebSocketHandler();
    }

    /**
     * 设置websocket参数大小，连接超时时间
     * @return
     */
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(48);
        container.setMaxBinaryMessageBufferSize(48);
//        container.setMaxSessionIdleTimeout(15 * 60 * 1000);
        return container;
    }
}
