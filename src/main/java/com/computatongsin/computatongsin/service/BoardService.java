package com.computatongsin.computatongsin.service;

import com.computatongsin.computatongsin.dto.ResponseDto;
import com.computatongsin.computatongsin.dto.req.BoardReqDto;
import com.computatongsin.computatongsin.dto.res.BoardResDto;
import com.computatongsin.computatongsin.dto.res.CommentResDto;
import com.computatongsin.computatongsin.dto.res.HeartNicknameResDto;
import com.computatongsin.computatongsin.entity.Board;
import com.computatongsin.computatongsin.entity.Comments;
import com.computatongsin.computatongsin.entity.Heart;
import com.computatongsin.computatongsin.entity.Member;
import com.computatongsin.computatongsin.repository.BoardRepository;
import com.computatongsin.computatongsin.repository.CommentRepository;
import com.computatongsin.computatongsin.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    private final HeartRepository heartRepository;

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

    // 게시판 전부 불러오기 (+댓글리스트)
    @Transactional
    public ResponseDto<?> getBoardListAndCommentList() {

        // 준비물
        List<Board> boardList = boardRepository.findAllByOrderByIdDesc();
        List<BoardResDto> boardResList = new ArrayList<>();

        for (Board board:boardList) {
            // 댓글 리스트는 계속 초기화 시켜줘야 한다
            List<CommentResDto> commentResDtoList = new ArrayList<>();
            // 꺼내온 게시글을 dto 에 담는다
            BoardResDto boardResDto = new BoardResDto(board);
            // 꺼내온 게시글로 댓글 리스트를 가져온다
            List<Comments> commentsList = commentRepository.findAllByBoard(board);

            for (Comments comment:commentsList) {
                // 꺼내온 댓글을 dto 에 담는다
                CommentResDto commentResDto = new CommentResDto(comment);
                // dto 에 담은 댓글들을 댓글 res 리스트에 담는다
                commentResDtoList.add(commentResDto);
            }
            // 댓글 dto 리스트에 담은 댓글들을 게시글 dto 에 담아준다
            boardResDto.updateCommentList(commentResDtoList);
            // 게시글 dto 리스트에 완료된 게시글들을 담아준다
            boardResList.add(boardResDto);
        }

        return ResponseDto.success(boardResList);
    }

    // 게시판 페이저로 불러오기 (페이지네이션)
    public ResponseDto<?> getBoardPagerList(int page,int size,String sortBy,boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Board> boardList = boardRepository.findAll(pageable);
        List<BoardResDto> boardResDtos = new ArrayList<>();
        for (Board board:boardList) {
            BoardResDto boardResDto = new BoardResDto(board);
            boardResDtos.add(boardResDto);
        }
        return ResponseDto.success(boardResDtos);
    }

    // 게시판 무한 스크롤로 불러오기
    public ResponseDto<?> getBoardInfiniteScroll(int page,int size,String sortBy,boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseDto.success(boardRepository.findAllByOrderById(pageable));
    }

    // 게시판 하나만 불러오기 (+해당 게시글 댓글) (+조회수 1 추가) (+해당 게시글 좋아요한 유저)
    @Transactional
    public ResponseDto<?> getBoard(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(()->new RuntimeException("게시글을 찾을 수 없습니다"));

        board.updateHit();

        List<Comments> commentsList = commentRepository.findAllByBoard(board);
        List<CommentResDto> commentResDtoList = new ArrayList<>();
        for (Comments comments:commentsList) {
            CommentResDto commentResDtoTemp = new CommentResDto(comments);
            commentResDtoList.add(commentResDtoTemp);
        }

        List<Heart> heartsList = heartRepository.findAllByBoard(board);
        List<HeartNicknameResDto> heartNicknameResDtoList = new ArrayList<>();
        for (Heart heart:heartsList) {
            HeartNicknameResDto heartNicknameResDto = new HeartNicknameResDto(heart);
            heartNicknameResDtoList.add(heartNicknameResDto);
        }

        BoardResDto boardResDto = new BoardResDto(board);
        boardResDto.updateCommentList(commentResDtoList);
        boardResDto.updateHeartList(heartNicknameResDtoList);
        return ResponseDto.success(boardResDto);
    }

    // 권한
    // 게시판 생성
    public ResponseDto<?> createBoard(BoardReqDto boardReqDto, Member member) {
        Board board = new Board(boardReqDto, member);
        BoardResDto boardResDto = new BoardResDto(board);
        boardRepository.save(board);
        return ResponseDto.success(boardResDto);
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

    // 게시판 제목 검색 기능
    public ResponseDto<?> searchBoard(String title) {
        List<Board> boardList = boardRepository.findAllByTitleContains(title);
        List<BoardResDto> boardResDtoList = new ArrayList<>();
        for (Board board:boardList) {
            BoardResDto boardResDto = new BoardResDto(board);
            boardResDtoList.add(boardResDto);
        }
        return ResponseDto.success(boardResDtoList);
    }

    // 게시판 제목 검색 + 페이지네이션
    public ResponseDto<?> searchBoardPage(String title, int page, int size, String sortBy, boolean isAsc) {

        // 페이저 조립
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // 검색 데이터 담기
        Page<Board> boardList = boardRepository.findAllByTitleContains(title, pageable);
        List<BoardResDto> boardResDtoList = new ArrayList<>();
        for (Board board:boardList) {
            BoardResDto boardResDto = new BoardResDto(board);
            boardResDtoList.add(boardResDto);
        }
        return ResponseDto.success(boardResDtoList);
    }
}
