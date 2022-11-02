package com.computatongsin.computatongsin.repository;

import com.computatongsin.computatongsin.entity.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
}