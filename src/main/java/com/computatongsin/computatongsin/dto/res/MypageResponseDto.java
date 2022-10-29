package com.computatongsin.computatongsin.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MypageResponseDto {

    private Long memberId;

    private String username;

    private String email;

    private List<String> writtenBoardList;

    private List<String> writtenCommentList;

    private List<String> heartList;

}
