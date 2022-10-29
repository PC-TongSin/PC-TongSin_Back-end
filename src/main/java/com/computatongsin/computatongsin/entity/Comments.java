package com.computatongsin.computatongsin.entity;

import com.computatongsin.computatongsin.dto.req.CommentReqDto;
import com.computatongsin.computatongsin.dto.req.SimpleCommentReqDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Comments extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @JsonIgnore
    @ManyToOne
    @JoinColumn( name = "member_id", nullable = false)
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Heart> HeartList = new java.util.ArrayList<>();


    public Comments(CommentReqDto commentReqDto, Member member, Board board ){
        this.content = commentReqDto.getContent();
        this.author = member.getUsername();
        this.member = member;
        this.board = board;
    }

    public void update( SimpleCommentReqDto commentReqDto) {
        this.content = commentReqDto.getContent();
    }
}