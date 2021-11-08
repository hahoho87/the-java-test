package com.hahoho87.thejavatest.study;

import com.hahoho87.thejavatest.domain.Member;
import com.hahoho87.thejavatest.domain.Study;
import com.hahoho87.thejavatest.member.MemberService;

public class StudyService {

    private final StudyRepository studyRepository;
    private final MemberService memberService;

    public StudyService(StudyRepository studyRepository, MemberService memberService) {
        this.studyRepository = studyRepository;
        this.memberService = memberService;
    }

    public Study createStudy(Long memberId, Study study) {
        Member member = memberService.findById(memberId);
        study.setOwner(member);
        return studyRepository.save(study);
    }
}
