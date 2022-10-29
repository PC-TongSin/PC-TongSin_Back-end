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

    /**
     * ObjectMapper는 기본 POJO(Plain Old Java Objects) 또는 범용 JSON 트리 모델(JsonNode) 간에 JSON을 읽고 쓰는 기능과 변환 수행을 위한 관련 기능을 제공합니다. 또한 다양한 스타일의 JSON 콘텐츠로 작업하고 다형성 및 개체 ID와 같은 고급 개체 개념을 지원하도록 고도로 사용자 정의할 수 있습니다. ObjectMapper는 고급 ObjectReader 및 ObjectWriter 클래스의 팩토리 역할도 합니다. Mapper(및 ObjectReader, 구성하는 ObjectWriter)는 JSON의 실제 읽기/쓰기를 구현하기 위해 JsonParser 및 JsonGenerator 인스턴스를 사용합니다. 대부분의 읽기 및 쓰기 메서드가 이 클래스를 통해 노출되지만 일부 기능은 ObjectReader 및 ObjectWriter를 통해서만 노출됩니다. 특히 더 긴 값 시퀀스의 읽기/쓰기는 ObjectReader.readValues(InputStream) 및 ObjectWriter.writeValues를 통해서만 사용할 수 있습니다. (출력 스트림).
     * 가장 간단한 사용법은 다음과 같습니다.
     *   최종 ObjectMapper 매퍼 = new ObjectMapper(); // 정적 싱글톤을 사용할 수 있습니다. 주입: 재사용해야 합니다!
     *   MyValue 값 = new MyValue();
     *   // ... 구성
     *   파일 newState = new File('my-stuff.json');
     *   mapper.writeValue(newState, 값); // MyValue 인스턴스의 JSON 직렬화를 씁니다.
     *   // 또는, 읽기
     *   MyValue 이전 = mapper.readValue(new File('my-older-stuff.json'), MyValue.class);
     *
     *   // 또는 JSON 트리 표현을 선호하는 경우:
     *   JsonNode 루트 = 매퍼.readTree(newState);
     *   // 예를 들어 JsonPointer 표현식을 사용하여 값을 찾습니다.
     *   정수 나이 = root.at('/개인/나이').getValueAsInt();
     * 주요 변환 API는 ObjectCodec에 정의되어 있으므로 이 클래스의 구현 세부 정보가 스트리밍 파서 및 생성기 클래스에 노출될 필요가 없습니다.
     * 그러나 ObjectCodec을 통한 사용은 일반적으로 ObjectMapper에 대한 종속성이 가능하지 않거나(Streaming API에서) 바람직하지 않은
     * 경우(Streaming API에만 의존할 때)에만 사용됩니다.
     * 매퍼 인스턴스는 모든 읽기 또는 쓰기 호출 전에 인스턴스의 모든 구성이 발생하는 경우 완전히 스레드로부터 안전합니다. 매퍼 인스턴스의
     * 구성이 처음 사용 후에 수정되면 변경 사항이 적용되거나 적용되지 않을 수 있으며 구성 호출 자체가 실패할 수 있습니다. 다른 구성을 사용해야 하는 경우 두 가지 주요 가능성이 있습니다.
     * 읽기에는 ObjectReader, 쓰기에는 ObjectWriter를 구성하고 사용합니다. 두 유형 모두 완전히 변경할 수 없으며 ObjectMapper의
     * 팩토리 메소드 또는 판독기/작성기 자체를 사용하여 다른 구성으로 새 인스턴스를 자유롭게 생성할 수 있습니다. 새로운 ObjectReader
     * 및 ObjectWriter의 생성은 매우 가벼운 작업이므로 일반적으로 JSON의 선택적 들여쓰기와 같은 것을 구성하기 위해 필요에 따라 호출별로
     * 생성하는 것이 적절합니다.
     * 특정 종류의 구성 가능성이 ObjectReader 및 ObjectWriter를 통해 사용할 수 없는 경우 대신 여러 ObjectMapper를 사용해야 할 수
     * 있습니다(예: 혼합 주석을 즉석에서 변경할 수 없거나 사용자 정의 (역)직렬 변환기 세트). 이 사용법을 돕기 위해 특정 구성으로 매퍼의
     * 복제본을 생성하고 복사된 인스턴스가 사용되기 전에 구성을 허용하는 메서드 copy()를 사용할 수 있습니다. 복사 작업은 새 ObjectMapper
     * 인스턴스를 구성하는 것만큼 비용이 많이 듭니다. 가능한 경우 여러 작업에 매퍼를 사용하려는 경우 여전히 매퍼를 풀링하고 재사용해야 합니다.
     * 캐싱에 대한 참고 사항: 루트 수준 역직렬 변환기는 항상 캐싱되고 전체(제네릭 인식) 유형 정보를 사용하여 액세스됩니다. 이것은 더
     * 제한적이며 모든 역직렬 변환기 유형의 하위 집합에 대해서만 수행되는 참조 유형의 캐싱과 다릅니다. 차이점의 주된 이유는 루트 수준에서
     * 들어오는 참조가 없고(따라서 참조 속성이 없고, 다른 역직렬 변환기를 생성하기 위한 참조 정보 또는 주석이 없음), 루트 수준에서 성능에
     * 가장 큰 영향을 미치기 때문입니다(기본적으로 캐시 관련된 디시리얼라이저의 전체 그래프).
     * 보안에 대한 참고 사항: '기본 입력' 기능 사용(enableDefaultTyping() 참조)은 신뢰할 수 없는 콘텐츠(신뢰할 수 없는 외부 당사자가
     * 생성한 콘텐츠)와 함께 사용하는 경우 잠재적인 보안 위험입니다. 그렇다면 사용자 정의 TypeResolverBuilder 구현을 구성하여
     * 인스턴스화할 수 있는 유형을 제한할 수 있습니다(setDefaultTyping 사용).
     */
    private final ObjectMapper mapper;
    private final ChatService service;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{

        String payload = message.getPayload();
        log.info("payload {}", payload);

        ChatDto chatMessage = mapper.readValue(payload, ChatDto.class);
        log.info("session {}", chatMessage.toString());

        ChatRoom room = service.findRoomById(chatMessage.getRoomId());
        log.info("room {}", room.toString());

        room.handleAction(session, chatMessage, service);
    }
}
