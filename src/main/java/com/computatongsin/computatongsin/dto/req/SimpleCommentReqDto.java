package com.computatongsin.computatongsin.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleCommentReqDto {

    @NotBlank(message = "댓글 내용이 필요합니다")
    private String content;

}
