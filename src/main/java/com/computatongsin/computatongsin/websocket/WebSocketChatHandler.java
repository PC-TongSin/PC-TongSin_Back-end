package com.computatongsin.computatongsin.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;
    private final ChatService service;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{

        // 작은 단위부터 조각 모음 하면서 값을 체크하고 있다
        String payload = message.getPayload();
        log.info("payload {}", payload);

        // readValue(String content, Class<T> valueType)
        ChatDto chatMessage = mapper.readValue(payload, ChatDto.class);
        // payload 역직렬화 대상, ChatDto 담아주는 애 ???
        log.info("session {}", chatMessage.toString());

        ChatRoom room = service.findRoomById(chatMessage.getRoomId());
        log.info("room {}", room.toString());
        // roomId, name, session

        // 역직렬화된 데이터가 들어간 채팅방으로 메세지 핸들링
        room.handleAction(session, chatMessage, service);
    }

}
