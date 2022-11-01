package com.computatongsin.computatongsin.dto.res;

import com.computatongsin.computatongsin.entity.Comments;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResDto {

    private Long board_id;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Long comment_id;
    private String author;
    private String content;

    public CommentResDto(Comments comment) {
        this.board_id = comment.getBoard().getId();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.comment_id = comment.getId();
        this.author = comment.getAuthor();
        this.content = comment.getContent();
    }
}
