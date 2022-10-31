package com.computatongsin.computatongsin.websocket;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoom {
    private String roomId;      // 채팅방 아이디
    private String name;        // 채팅방 이름
    private Set<WebSocketSession> sessions = new HashSet<>();
    // HashSet. HashTable, 중복 불허, 순서 X

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handleAction(WebSocketSession session, ChatDto message, ChatService service) {
        // 메세지에 담긴 타입 확인
        // 메세지의 Type이 Dto의 Type에 있는 ENTER Enum과 동일하다면
        if (message.getType().equals(ChatDto.MessageType.ENTER)) {
            // Session에 넘어온 Session을 담는다
            sessions.add(session);
            // 메세지에는 입장했다는 메시지를 띄움
            message.setMessage((message.getSender() + " 님이 입장하셨습니다"));
            sendMessage(message, service);
        } else if (message.getType().equals(ChatDto.MessageType.TALK)) {
            message.setMessage(message.getMessage());
            sendMessage(message, service);
        }
    }

    private <T> void sendMessage(T message, ChatService service) {
        sessions.parallelStream().forEach(session -> service.sendMessage(session, message));
    }

}
