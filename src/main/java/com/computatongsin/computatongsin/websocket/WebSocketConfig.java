package com.computatongsin.computatongsin.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메세지 topic이 일어나는 곳에 쓰일 prefix 심플브로커
        config.enableSimpleBroker("/topic");
        
        // @MessageMapping 용, 마치 @RequestMapping과 같은 느낌의 어노테이션
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins("http://localhost:3000").withSockJS();
        // setAllowedOrigins 에서 * 아스타 사용시 오류 뿜어냄. 호스트를 특정지어 줘야 한다.
        // 프론트에서 사용 중인 SockJs url 주소 세팅
//        .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js");
        // 웹 소캣 허용 설정
//        registry.addEndpoint("/hello").setAllowedOrigins("*").withSockJS();
    }

}