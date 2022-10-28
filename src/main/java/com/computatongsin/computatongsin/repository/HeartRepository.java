package com.computatongsin.computatongsin.repository;

import com.computatongsin.computatongsin.entity.Board;
import com.computatongsin.computatongsin.entity.Heart;
import com.computatongsin.computatongsin.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    boolean existsByBoardAndMember(Board boardId, Member member);
    void deleteByBoardAndMember(Board boardId, Member member);
    List<Heart> findAllByMember(Member member);
    Page<Heart> findAllByMember(Member member, Pageable pageable);
}
