package com.computatongsin.computatongsin.dto.res;

import com.computatongsin.computatongsin.entity.Board;
import com.computatongsin.computatongsin.entity.Comments;
import com.computatongsin.computatongsin.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class BoardResDto {

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int totalCommentCount;
    private int totalHeartCount;
    private Long id;
    private String title;
    private String content;
    private String author;
    private long hit;

    private String nickname;

    // 읽기 전용
    private List<CommentResDto> commentResDtoList = new ArrayList<>();

    public void updateCommentList(List<CommentResDto> commentsList) {
        this.commentResDtoList = commentsList;
    }

    public BoardResDto(Board board) {
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.totalCommentCount = board.getTotalCommentCount();
        this.totalHeartCount = board.getTotalHeartCount();
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getAuthor();
        this.hit = board.getHit();
        this.nickname = board.getNickname();
    }
}
