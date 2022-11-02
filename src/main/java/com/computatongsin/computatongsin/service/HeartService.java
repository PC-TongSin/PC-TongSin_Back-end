package com.computatongsin.computatongsin.service;

import com.computatongsin.computatongsin.dto.ResponseDto;
import com.computatongsin.computatongsin.dto.res.HeartNicknameResDto;
import com.computatongsin.computatongsin.entity.Board;
import com.computatongsin.computatongsin.entity.Heart;
import com.computatongsin.computatongsin.entity.Member;
import com.computatongsin.computatongsin.repository.BoardRepository;
import com.computatongsin.computatongsin.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public ResponseDto<?> heart(Long id, Member member) {

        Board board = boardRepository.findById(id).orElseThrow(()->new RuntimeException("게시글을 찾을 수 없습니다"));

        if(heartRepository.existsByBoardAndMember(board, member)){
            heartRepository.deleteByBoardAndMember(board, member);
            return ResponseDto.success("좋아요 삭제 완료");
        } else {
            Heart heart = new Heart(member, board);
            heartRepository.save(heart);
            return ResponseDto.success("좋아요 생성 완료");
        }
    }

    // 해당 게시글을 좋아요한 유저 명단
    @Transactional
    public ResponseDto<?> hasUsers(Long id) {

        Optional<Board> boardTemp = boardRepository.findById(id);
        if (boardTemp.isEmpty()) {
            return ResponseDto.fail("요청한 게시글의 아이디로 게시글을 찾을 수 없습니다");
        }
        Board board = boardTemp.get();

        List<Heart> heartList = heartRepository.findAllByBoard(board);

        List<HeartNicknameResDto> heartUsersDtoList = new ArrayList<>();
        for (Heart heart:heartList) {
            HeartNicknameResDto heartUsersDto = new HeartNicknameResDto(heart);
            heartUsersDtoList.add(heartUsersDto);
        }

        return ResponseDto.success(heartUsersDtoList);
    }
}
