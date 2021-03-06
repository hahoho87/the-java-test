package com.hahoho87.thejavatest.member;

import com.hahoho87.thejavatest.domain.Member;
import com.hahoho87.thejavatest.domain.Study;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public void notify(Study study) {
    }

    public void validate(Study study) {

    }

    public void notify(Member member) {

    }
}
