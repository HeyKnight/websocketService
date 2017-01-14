package com.ancun;

import com.ancun.websocket.TelecomWebSocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableAutoConfiguration
@EnableWebSocket
public class WebsocketServiceApplication extends SpringBootServletInitializer
		implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(telecomWebSocketHandler(), "/websocket").setAllowedOrigins("http://127.0.0.1").withSockJS();
	}

	@Bean
	public WebSocketHandler telecomWebSocketHandler() {
		return new TelecomWebSocketHandler();
	}

	public static void main(String[] args) {
		SpringApplication.run(WebsocketServiceApplication.class, args);
	}
}
