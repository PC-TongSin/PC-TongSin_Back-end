package com.computatongsin.computatongsin.websocket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Greeting {

    private String content;

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    // {"nickname":"tester : 아아아한글조라","content":null}
}