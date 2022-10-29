package com.computatongsin.computatongsin.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class SpringConfig implements WebSocketConfigurer {

    // WebSocketHandler 에 관한 생성자 추가
    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // EndPoint 설정 : /ws/chat
        // EndPoint로 ws://localhost:8080/ws/chat 으로 요청이 들어오면 WebSocket 통신을 진행한다
        registry.addHandler(webSocketHandler,"/ws/chat").setAllowedOrigins("*");
    }
}
