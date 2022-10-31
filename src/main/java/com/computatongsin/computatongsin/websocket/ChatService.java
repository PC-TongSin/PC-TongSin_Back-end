package com.computatongsin.computatongsin.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@Data
@Service
public class ChatService {

    private final ObjectMapper mapper;
    private Map<String, ChatRoom> chatRooms;

    /**
     * PostConstruct 주석은 초기화를 수행하기 위해 종속성 주입이 완료된 후 실행해야 하는 메서드에 사용됩니다. 이 메서드는 클래스가 서비스되기 전에 호출되어야 합니다. 이 주석은 종속성 주입을 지원하는 모든 클래스에서 지원되어야 합니다. 클래스가 주입할 리소스를 요청하지 않는 경우에도 PostConstruct로 주석이 달린 메서드를 호출해야 합니다. 주어진 클래스의 한 메소드에만 이 주석을 달 수 있습니다. PostConstruct 주석이 적용되는 메서드는 다음 기준을 모두 충족해야 합니다.
     * 메소드는 인터셉터 사양에 정의된 대로 InvocationContext 객체를 사용하는 인터셉터의 경우를 제외하고 매개변수를 갖지 않아야 합니다.
     * 인터셉터 클래스 또는 인터셉터 클래스의 수퍼 클래스에 정의된 메서드에는 다음 서명 중 하나가 있어야 합니다.
     * 무효 <METHOD>(InvocationContext)
     * 개체 <METHOD>(InvocationContext)에서 예외가 발생합니다.
     * 참고: PostConstruct 인터셉터 메소드는 애플리케이션 예외를 throw해서는 안 되지만 동일한 인터셉터 메소드가 수명 주기 이벤트 외에 비즈니스 또는 타임아웃 메소드에 개입하는 경우 java.lang.Exception을 포함하여 확인된 예외를 throw하도록 선언될 수 있습니다. PostConstruct 인터셉터 메서드가 값을 반환하면 컨테이너에서 무시됩니다.
     * 비 인터셉터 클래스에 정의된 메서드에는 다음 서명이 있어야 합니다.
     * 무효 <메소드>()
     * PostConstruct 주석이 적용되는 방법은 공개, 보호, 패키지 비공개 또는 비공개일 수 있습니다.
     * 메서드는 응용 프로그램 클라이언트를 제외하고 정적이 아니어야 합니다.
     * 이 방법은 최종적이지 않아야 합니다.
     * 메서드가 확인되지 않은 예외를 throw하면 예외가 인터셉터에 의해 처리되는 경우를 제외하고 클래스를 서비스에 넣으면 안 됩니다.
     */

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    // 모든 채팅방 찾기
    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    // 채팅방 한개 찾기
    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    // 채팅방 만들기
    public ChatRoom createRoom(String name) {

        // 랜덤 방 아이디 생성
        String roomId = UUID.randomUUID().toString();

        // 채팅룸 빌드
        ChatRoom room = ChatRoom.builder()
                // 채팅룸 번호
                .roomId(roomId)
                // 채팅룸 이름
                .name(name)
                .build();

        // 채팅룸 맵에 넣는다 Map<String, ChatRoom> chatRooms;
        // 위에서 만든 랜덤 아이디값을 키값으로 ChatRoom을 객체화한 값으로 맵으로 만든다
        chatRooms.put(roomId, room);
        // 객체화된 ChatRoom 채팅룸 내부는 세션, 클라이언트로부터 받아온 역직렬화한 페이로드 메세지값이 들어간다
        return room;
    }

/**
 * OjectMapper Class > writeValueAsString
 * Java 값을 문자열로 직렬화하는 데 사용할 수 있는 메소드입니다. 기능적으로는 StringWriter로 writeValue(Writer, Object)를 호출하고 String을 구성하는 것과 동일하지만 더 효율적입니다.
 * 참고: 버전 2.1 이전에는 throws 절에 IOException이 포함되었습니다. 2.1 제거했습니다.
 */

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
