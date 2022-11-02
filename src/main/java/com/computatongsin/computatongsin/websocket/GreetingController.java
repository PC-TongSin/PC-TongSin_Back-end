package com.computatongsin.computatongsin.websocket;


import com.computatongsin.computatongsin.security.MemberDetails;
import com.computatongsin.computatongsin.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final TokenProvider tokenProvider;

    /**
     * htmlEscape
     * 특수 문자를 HTML 문자 참조로 변환합니다.
     * HTML 4.01 권장 사항에 정의된 전체 문자 집합을 처리합니다.
     * 모든 특수 문자를 해당 엔티티 참조(예: <)로 이스케이프합니다.
     * 참조: https://www.w3.org/TR/html4/sgml/entities.html
     * 매개변수:
     * input – (이스케이프 처리되지 않은) 입력 문자열
     * 보고:
     * 이스케이프된 문자열
     */

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay

        System.out.println("메세지 : " + message.getContent());
        System.out.println("토큰 : " + message.getAccesstoken());  // 여기서 null 옴
//        System.out.println("닉네임 : " + memberDetails.getMember().getNickname());

        // 원래 토큰 받던 방식으로 하는 경우
//        String nickname = memberDetails.getMember().getNickname();

        // 보내준 토큰 분해해서 떼어낼 거 떼고 닉네임만 뽑아줌
        String nickname = tokenProvider.getMemberNicknameInToken(message.getAccesstoken());
        System.out.println(nickname);

        // body로 오는 경우 (HelloMessage 객체 멤버 변수, 메소드 추가 필요)
//        String token = message.getAccesstoken();
//        String nickname = tokenProvider.getMemberNicknameInToken(token);
//        System.out.println(memberDetails.getMember().getNickname());

        // 채팅을 보낸 시간 입력
        LocalDateTime now = LocalDateTime.now();  // 2021-06-17T06:43:21.419878100
        String formatedNow = now.format(DateTimeFormatter.ofPattern("HH시 mm분 ss초"));

        return new Greeting("[" +formatedNow + "] " + nickname + " : " + HtmlUtils.htmlEscape(message.getContent()));
        // 출력 예상 폼 >> [2021년 06월 17일 06시 43분 21초] 지존무쌍 : 하하하 내가 최강이다
    }
}