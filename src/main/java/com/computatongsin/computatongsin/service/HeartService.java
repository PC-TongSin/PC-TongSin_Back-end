package com.computatongsin.computatongsin.service;

import com.computatongsin.computatongsin.dto.ResponseDto;
import com.computatongsin.computatongsin.entity.Board;
import com.computatongsin.computatongsin.entity.Heart;
import com.computatongsin.computatongsin.entity.Member;
import com.computatongsin.computatongsin.repository.BoardRepository;
import com.computatongsin.computatongsin.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;

    private final BoardRepository boardRepository;

    @Transactional
    public ResponseDto<?> heart(Long boardId, Member member) {

        Board board = boardRepository.findById(boardId).orElseThrow();

        if(heartRepository.existsByBoardAndMember(board, member)){
            heartRepository.deleteByBoardAndMember(board, member);
            return ResponseDto.success("좋아요 삭제 완료");
        } else {
            Heart heart = new Heart(member, board);
            heartRepository.save(heart);
            return ResponseDto.success("좋아요 생성 완료");
        }
    }
}