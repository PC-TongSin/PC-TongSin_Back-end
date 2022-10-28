package com.computatongsin.computatongsin.service;

import com.computatongsin.computatongsin.dto.ResponseDto;
import com.computatongsin.computatongsin.dto.req.BoardReqDto;
import com.computatongsin.computatongsin.dto.res.BoardResDto;
import com.computatongsin.computatongsin.entity.Board;
import com.computatongsin.computatongsin.entity.Comments;
import com.computatongsin.computatongsin.entity.Member;
import com.computatongsin.computatongsin.repository.BoardRepository;
import com.computatongsin.computatongsin.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 게시판 전부 불러오기
    @Transactional
    public ResponseDto<?> getBoardList() {
        List<Board> boardList = boardRepository.findAllByOrderByIdDesc();
        List<BoardResDto> boardResDtos = new ArrayList<>();
        for (Board board:boardList) {
            BoardResDto boardResDto = new BoardResDto(board);
            boardResDtos.add(boardResDto);
        }
        return ResponseDto.success(boardResDtos);
    }

    // 게시판 전부 불러오기 (+댓글)
    @Transactional
    public ResponseDto<?> getBoardListAndCommentList() {
        return ResponseDto.success(boardRepository.findAllByOrderByIdDesc());
    }

    // 게시판 페이지로 불러오기
    public ResponseDto<?> getBoardPagerList(int page,int size,String sortBy,boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseDto.success(boardRepository.findAll(pageable));
    }

    // 게시판 무한 스크롤로 불러오기
    public ResponseDto<?> getBoardInfiniteScroll(int page,int size,String sortBy,boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseDto.success(boardRepository.findAllByOrderById(pageable));
    }

    // 게시판 하나만 불러오기 (+해당 게시글 댓글) (+조회수 1 추가)
    @Transactional
    public ResponseDto<?> getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(()->new RuntimeException("게시글을 찾을 수 없습니다"));
        board.updateHit();
        List<Comments> commentsList = commentRepository.findAllByBoard(board);
        board.updateCommentList(commentsList);
        return ResponseDto.success(board);
    }

    // 권한
    // 게시판 생성
    public ResponseDto<?> createBoard(BoardReqDto boardReqDto, Member member) {
        Board board = new Board(boardReqDto, member);
        return ResponseDto.success(boardRepository.save(board));
    }

    // 권한
    // 게시판 수정
    @Transactional
    public ResponseDto<?> editBoard(Long id, BoardReqDto boardReqDto, Member member) {
        Board board = boardRepository.findById(id).orElseThrow(()->new RuntimeException("수정할 게시글이 없습니다"));
        if (!board.getMember().getUsername().equals(member.getUsername())) {
            throw new RuntimeException("본인이 작성한 글만 수정 가능합니다");
        }
        board.update(boardReqDto);
        boardRepository.save(board);
        return ResponseDto.success("게시글 수정 완료");
    }

    // 권한
    // 게시판 삭제
    @Transactional
    public ResponseDto<?> deleteBoard(Long id, Member member) {
        Board board = boardRepository.findById(id).orElseThrow(()->new RuntimeException("삭제할 게시글이 없습니다"));
        if (!board.getMember().getUsername().equals(member.getUsername())) {
            throw new RuntimeException("본인이 작성한 글만 삭제 가능합니다");
        }
        boardRepository.deleteById(id);
        return ResponseDto.success("게시글 삭제 완료");
    }

}
