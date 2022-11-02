package com.computatongsin.computatongsin.websocket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class WebsocketTokenDto {

    @NotNull(message = "토큰이 없으면 유저를 알 수가 없쥬?")
    private String accesstoken;
}
