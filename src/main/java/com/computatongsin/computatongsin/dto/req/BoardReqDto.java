package com.computatongsin.computatongsin.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BoardReqDto {

    @NotBlank(message = "제목이 없습니다")
    private String title;
    @NotBlank(message = "내용이 없습니다")
    private String content;

}
