package com.computatongsin.computatongsin.repository;

import com.computatongsin.computatongsin.entity.Board;
import com.computatongsin.computatongsin.entity.Comments;
import com.computatongsin.computatongsin.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByBoard(Board board);
    List<Comments> findAllByMember(Member member);
    Page<Comments> findAllByMember(Member member, Pageable pageable);
}
