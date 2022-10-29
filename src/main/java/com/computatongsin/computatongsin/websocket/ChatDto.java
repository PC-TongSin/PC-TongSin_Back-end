package com.computatongsin.computatongsin.websocket;

import lombok.Builder;
import lombok.Data;

import java.awt.*;
import java.util.Date;

/**
 * @Data 설명
 * 모든 필드에 대한 getter, 유용한 toString 메서드 및 hashCode를 생성하고 모든 비일시적 필드를 확인하는 구현과 동일합니다. 또한 생성자뿐만 아니라 모든 비 최종 필드에 대한 설정자를 생성합니다.
 * @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode와 동일합니다.
 * 전체 문서는 @Data 에 대한 프로젝트 롬복 기능 페이지에서 찾을 수 있습니다.
 */
@Data
@Builder
public class ChatDto {
    // 메세지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER,
        TALK
    }

    private MessageType type;   // 메세지 타입
    private String roomId;      // 방 번호
    private String sender;      // 메세지 보낸 사람
    private String message;     // 메세지
    private String time;        // 채팅 발송 시간
//    private S3ImageUrl imageUrl;

//    long nowTime = new Date().getTime();    현재 채팅 올린시간이 올라가도록 time 값에 넣어주면 될 것 같고
}
