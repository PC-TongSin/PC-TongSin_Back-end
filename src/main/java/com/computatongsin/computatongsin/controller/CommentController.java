package com.computatongsin.computatongsin.controller;

import com.computatongsin.computatongsin.dto.ResponseDto;
import com.computatongsin.computatongsin.dto.req.CommentReqDto;
import com.computatongsin.computatongsin.dto.req.SimpleCommentReqDto;
import com.computatongsin.computatongsin.security.MemberDetails;
import com.computatongsin.computatongsin.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성 (권한 필요)
    @PostMapping("/comments")
    public ResponseDto<?> createComment(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody @Valid CommentReqDto commentReqDto) {
        return commentService.createComment(memberDetails.getMember(), commentReqDto);
    }

    // 댓글 수정하기 (권한 필요)
    @PutMapping("/comments/{id}")
    public ResponseDto<?> editComment(
            @PathVariable Long id,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody @Valid SimpleCommentReqDto commentReqDto) {
        return commentService.editComment(id, memberDetails.getMember(), commentReqDto);
    }

    // 댓글 삭제하기 (권한 필요)
    @DeleteMapping("/comments/{id}")
    public ResponseDto<?> deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        return commentService.deleteComment(id, memberDetails.getMember());
    }
}