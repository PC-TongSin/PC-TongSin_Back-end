package com.computatongsin.computatongsin.dto.res;

import com.computatongsin.computatongsin.entity.Heart;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeartNicknameResDto {
    private String username;
    private String nickname;

    public HeartNicknameResDto(Heart heart) {
        this.username = heart.getMember().getUsername();
        this.nickname = heart.getMember().getNickname();
    }
}
