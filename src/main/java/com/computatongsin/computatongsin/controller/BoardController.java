package com.computatongsin.computatongsin.controller;

import com.computatongsin.computatongsin.dto.ResponseDto;
import com.computatongsin.computatongsin.dto.req.BoardReqDto;
import com.computatongsin.computatongsin.security.MemberDetails;
import com.computatongsin.computatongsin.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    // 게시판 전체 조회
    @GetMapping("/boards")
    public ResponseDto<?> getBoardList() {
        return boardService.getBoardList();
    }

    // 게시판 전체 조회 (+댓글)
    @GetMapping("/boards/comments")
    public ResponseDto<?> getBoardListAndCommentList() {
        return boardService.getBoardListAndCommentList();
    }

    // 게시판 전체 페이징 조회 GET /boards/pager?page=3&size=10&sortBy=id&isAsc=true  + Board Total Count
    @GetMapping ("/boards/pager")
    public ResponseDto<?> getBoardPagerList(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc) {
        int pageTemp = page - 1;
        return boardService.getBoardPagerList(pageTemp,size,sortBy,isAsc);
    }

    // 게시판 전체 무한 스크롤 조회 GET
    @GetMapping ("/boards/infinites")
    public ResponseDto<?> getBoardInfinite(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc) {
        int pageTemp = page - 1;
        return boardService.getBoardInfiniteScroll(pageTemp, size, sortBy, isAsc);
    }

    // 게시판 하나만 가져오기
    @GetMapping("/boards/{id}")
    public ResponseDto<?> getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    // 게시판 생성 (권한 필요)
    @PostMapping("/boards")
    public ResponseDto<?> createBoard(
            @RequestBody @Valid BoardReqDto boardReqDto,
            @AuthenticationPrincipal MemberDetails memberDetails
            ) {
        System.out.println(memberDetails.getMember().getNickname());
        return boardService.createBoard(boardReqDto, memberDetails.getMember());
    }

    // 게시판 수정 (권한 필요)
    @PutMapping("/boards/{id}")
    public ResponseDto<?> editBoard(
            @PathVariable Long id,
            @RequestBody BoardReqDto boardReqDto,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        return boardService.editBoard(id, boardReqDto, memberDetails.getMember());
    }

    // 게시판 삭제 (권한 필요)
    @DeleteMapping("/boards/{id}")
    public ResponseDto<?> deleteBoard(
            @PathVariable Long id,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        return boardService.deleteBoard(id, memberDetails.getMember());
    }

    // 검색 기능 (게시글 제목으로 검색)    /boards/search?title=String 변수 검색할 거
    @GetMapping("/boards/search")
    public ResponseDto<?> searchBoard(@RequestParam("title") String title) {
        return boardService.searchBoard(title);
    }

    // 페이저로 타이틀로 검색하기 기능
    @GetMapping("/boards/search/pager")
    public ResponseDto<?> searchBoardPage(@RequestParam("title") String title,
                                          @RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size,
                                          @RequestParam("sortBy") String sortBy,
                                          @RequestParam("isAsc") boolean isAsc) {
        int pageTemp = page - 1;
        return boardService.searchBoardPage(title, pageTemp, size, sortBy, isAsc);
    }
}
