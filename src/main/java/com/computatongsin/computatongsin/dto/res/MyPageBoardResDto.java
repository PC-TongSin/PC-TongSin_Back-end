package com.computatongsin.computatongsin.dto.res;

import com.computatongsin.computatongsin.entity.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class MyPageBoardResDto {

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int totalCommentCount;
    private int totalHeartCount;
    private Long id;
    private String title;
    private String content;
    private String author;

    public MyPageBoardResDto(Board board) {
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.totalCommentCount = board.getTotalCommentCount();
        this.totalHeartCount = board.getTotalHeartCount();
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getAuthor();
    }
}
