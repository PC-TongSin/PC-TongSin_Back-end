package com.computatongsin.computatongsin.dto.req;

import com.computatongsin.computatongsin.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class MemberDto {

    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Long member_id;
    private String username;
    private String nickname;


    public MemberDto(Member member) {
        this.createAt = member.getCreatedAt();
        this.modifiedAt = member.getModifiedAt();
        this.member_id = member.getMember_id();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
    }
}
