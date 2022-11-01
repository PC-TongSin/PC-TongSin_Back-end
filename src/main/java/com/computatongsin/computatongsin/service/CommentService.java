package com.computatongsin.computatongsin.service;

import com.computatongsin.computatongsin.dto.ResponseDto;
import com.computatongsin.computatongsin.dto.req.CommentReqDto;
import com.computatongsin.computatongsin.dto.req.SimpleCommentReqDto;
import com.computatongsin.computatongsin.dto.res.CommentResDto;
import com.computatongsin.computatongsin.dto.res.MypageResponseDto;
import com.computatongsin.computatongsin.entity.Board;
import com.computatongsin.computatongsin.entity.Comments;
import com.computatongsin.computatongsin.entity.Member;
import com.computatongsin.computatongsin.repository.BoardRepository;
import com.computatongsin.computatongsin.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 작성 (권한)
    @Transactional
    public ResponseDto<?> createComment(Member member, CommentReqDto commentReqDto) {
        Board board = boardRepository.findById(commentReqDto.getBoard_id()).orElseThrow(()->new RuntimeException("게시글이 존재하지 않습니다"));
        Comments comments = new Comments(commentReqDto, member, board);
        commentRepository.save(comments);
        CommentResDto commentResDto = new CommentResDto(comments);
        return ResponseDto.success(commentResDto);
    }

    // 댓글 수정 (권한)
    @Transactional
    public ResponseDto<?> editComment(Long id, Member member, SimpleCommentReqDto commentReqDto) {
        Comments comments = commentRepository.findById(id).orElseThrow(
                ()->new RuntimeException("댓글을 찾을 수 없습니다")
        );
        if(!comments.getMember().getUsername().equals(member.getUsername())) {
            throw new RuntimeException("본인이 작성한 댓글이 아닙니다");
        }
        comments.update(commentReqDto);
        commentRepository.save(comments);
        return ResponseDto.success(comments);
    }

    // 댓글 삭제 (권한)
    @Transactional
    public ResponseDto<?> deleteComment(Long id, Member member) {
        Comments comments = commentRepository.findById(id).orElseThrow(()->new RuntimeException("댓글을 찾을 수 없습니다"));

        if(!comments.getMember().getUsername().equals(member.getUsername())) {
            throw new RuntimeException("본인이 작성한 댓글이 아닙니다");
        }
        commentRepository.deleteById(id);
        return ResponseDto.success("댓글 삭제 완료");
    }

    // DB의 전체 댓글 조회
    public ResponseDto<?> getCommentList() {
        List<Comments> commentsList = commentRepository.findAllByOrderByIdDesc();
        List<CommentResDto> commentResDtoList = new ArrayList<>();
        for(Comments comments : commentsList){
            CommentResDto commentResDto = new CommentResDto(comments);
            commentResDtoList.add(commentResDto);
        }
        return ResponseDto.success(commentResDtoList);
    }

    // 특정 게시글의 아이디로 댓글 리스트
    @Transactional
    public ResponseDto<?> getTargetBoardCommentList(Long id) {
        // 준비물
        Optional<Board> boardTemp = boardRepository.findById(id);
        List<CommentResDto> commentResDtoList = new ArrayList<>();
        // 게시글 null 체크
        if (boardTemp.isEmpty()) {
            return ResponseDto.fail("요청한 게시글의 아이디로 게시글을 찾을 수 없습니다");
        }
        // 체크 확인 후 Optional 해제
        Board board = boardTemp.get();
        // 댓글 List Dto 에 담아준다
        List<Comments> commentsList = commentRepository.findAllByBoard(board);
        for (Comments comments:commentsList) {
            CommentResDto commentResDto = new CommentResDto(comments);
            commentResDtoList.add(commentResDto);
        }
        return ResponseDto.success(commentResDtoList);
    }
}
