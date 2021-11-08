package com.hahoho87.thejavatest.study;

import com.hahoho87.thejavatest.domain.Member;
import com.hahoho87.thejavatest.domain.Study;
import com.hahoho87.thejavatest.member.MemberService;

import java.util.Optional;

public class StudyService {

    private final StudyRepository studyRepository;
    private final MemberService memberService;

    public StudyService(StudyRepository studyRepository, MemberService memberService) {
        this.studyRepository = studyRepository;
        this.memberService = memberService;
    }

    public Study createStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id : " + memberId)));
        return studyRepository.save(study);
    }
}
