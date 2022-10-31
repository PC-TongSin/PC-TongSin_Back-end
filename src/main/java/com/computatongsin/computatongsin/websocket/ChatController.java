package com.computatongsin.computatongsin.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService service;

    // 웹소켓으로 된 채팅방으로 만든다
    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return service.createRoom(name);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return service.findAllRoom();
    }

}
