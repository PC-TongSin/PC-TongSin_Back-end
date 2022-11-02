package com.computatongsin.computatongsin.websocket;

import com.computatongsin.computatongsin.security.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HelloMessage {
    private String content;
    private String accesstoken;
}