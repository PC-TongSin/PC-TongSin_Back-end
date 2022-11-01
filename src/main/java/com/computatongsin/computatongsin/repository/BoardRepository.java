package com.computatongsin.computatongsin.repository;

import com.computatongsin.computatongsin.entity.Board;
import com.computatongsin.computatongsin.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Long> {
    Slice<Board> findAllByOrderById(Pageable pageable);
    List<Board> findAllByMember(Member member);
    Page<Board> findAllByMember(Member member, Pageable pageable);
    Optional<Board> findById(Long id);
    List<Board> findAllByOrderByIdDesc();
    List<Board> findAllByTitleContains(String title);
    Page<Board> findAllByTitleContains(String title, Pageable pageable);
}