package com.hahoho87.thejavatest.study;

import com.hahoho87.thejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock StudyRepository studyRepository;
    @Mock MemberService memberService;

    @Test
    void createStudyServiceTest() {
        StudyService studyService = new StudyService(studyRepository, memberService);
        assertThat(studyService).isNotNull();
    }

}