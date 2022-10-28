package com.computatongsin.computatongsin.service;

import com.computatongsin.computatongsin.dto.ResponseDto;
import com.computatongsin.computatongsin.dto.res.MyPageBoardResDto;
import com.computatongsin.computatongsin.entity.Board;
import com.computatongsin.computatongsin.entity.Heart;
import com.computatongsin.computatongsin.entity.Member;
import com.computatongsin.computatongsin.repository.BoardRepository;
import com.computatongsin.computatongsin.repository.CommentRepository;
import com.computatongsin.computatongsin.repository.HeartRepository;
import com.computatongsin.computatongsin.repository.MemberRepository;
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
public class MyPageService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;

    // 내 게시글 불러오기
    public ResponseDto<?> getMyBoardList(Member member) {
        List<Board> boardList = boardRepository.findAllByMember(member);
        List<MyPageBoardResDto> myPageBoardListResDto = new ArrayList<>();
        for (Board board : boardList) {
            MyPageBoardResDto myPageBoardResDto = new MyPageBoardResDto(board);
            myPageBoardListResDto.add(myPageBoardResDto);
        }
        return ResponseDto.success(myPageBoardListResDto);
    }

    // 내 댓글 불러오기
    public ResponseDto<?> getMyCommentList(Member member) {
        return ResponseDto.success(commentRepository.findAllByMember(member));
    }

    // 내가 좋아요한 게시글 불러오기
    @Transactional
    public ResponseDto<?> getMyHeartList(Member member) {

        List<Heart> heartList = heartRepository.findAllByMember(member);

        List<MyPageBoardResDto> myPageBoardResDtos = new ArrayList<>();

        for (Heart heart:heartList) {
            Long board_id = heart.getBoard().getId();
            Board board = boardRepository.findById(board_id).orElseThrow(()->new RuntimeException("게시글을 찾을 수 없습니다"));
            MyPageBoardResDto myPageBoardResDto = new MyPageBoardResDto(board);
            myPageBoardResDtos.add(myPageBoardResDto);
        }

        return ResponseDto.success(myPageBoardResDtos);
    }

    // 내가 작성한 게시글 불러오기 (페이저)
    @Transactional
    public ResponseDto<?> getMyBoardPagerList(int page,int size,String sortBy,boolean isAsc, Member member) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseDto.success(boardRepository.findAllByMember(member, pageable));
    }

    // 내가 작성한 댓글 불러오기 (페이저)
    @Transactional
    public ResponseDto<?> getMyCommentPagerList(int page,int size,String sortBy,boolean isAsc, Member member) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseDto.success(commentRepository.findAllByMember(member, pageable));
    }

    // 내가 좋아요한 게시글 불러오기 (페이저)
    @Transactional
    public ResponseDto<?> getMyHeartPagerList(int page,int size,String sortBy,boolean isAsc, Member member) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Heart> heartList = heartRepository.findAllByMember(member, pageable);

        List<MyPageBoardResDto> myPageBoardResDtos = new ArrayList<>();

        for (Heart heart:heartList) {
            Long board_id = heart.getBoard().getId();
            Board board = boardRepository.findById(board_id).orElseThrow(()->new RuntimeException("있쥬?"));
            MyPageBoardResDto myPageBoardResDto = new MyPageBoardResDto(board);
            myPageBoardResDtos.add(myPageBoardResDto);
        }

        return ResponseDto.success(myPageBoardResDtos);
    }

}
