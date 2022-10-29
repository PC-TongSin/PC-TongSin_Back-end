package com.computatongsin.computatongsin.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor //멤버 변수를 하나만 쓸 때.
public class MypageRequestDto {

    @NotBlank
    private String nickname;


}
