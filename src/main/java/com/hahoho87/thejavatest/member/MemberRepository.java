package com.hahoho87.thejavatest.member;

import com.hahoho87.thejavatest.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}