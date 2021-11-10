package com.hahoho87.thejavatest.study;

import com.hahoho87.thejavatest.domain.Member;
import com.hahoho87.thejavatest.domain.Study;
import com.hahoho87.thejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock StudyRepository studyRepository;
    @Mock MemberService memberService;

    @Test
    void createStudyServiceTest() {
        StudyService studyService = new StudyService(studyRepository, memberService);
        assertThat(studyService).isNotNull();
    }

    @Test
    void stubbingMockTest() {
        Member member = new Member();
        member.setMemberId(1L);
        member.setName("hahoho");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        Optional<Member> byId = memberService.findById(1L);
        assertThat(byId.get()).isEqualTo(member);
    }

    @Test
    void stubbingMockTestWithOrder() {

        Member member = new Member();
        member.setMemberId(1L);
        member.setName("hahoho");

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new IllegalArgumentException())
                .thenReturn(Optional.empty()) ;

        Optional<Member> byId1 = memberService.findById(1L);
        assertThat(byId1.get()).isEqualTo(member);
        assertThatThrownBy(() -> memberService.findById(1L)).isInstanceOf(IllegalArgumentException.class);
        Optional<Member> byId3 = memberService.findById(1L);
        assertThat(byId3).isEqualTo(Optional.empty());
    }

    @Test
    void stubbingTestExam() {
        Study study = new Study(10, "test");

        Member member = new Member(1L, "testMember");
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        StudyService studyService = new StudyService(studyRepository, memberService);
        studyService.createStudy(1L, study);

        assertThat(study.getOwner()).isNotNull();
        assertThat(study.getOwner()).isEqualTo(member);
    }

    @Test
    void createNewStudyTest() {
        Member member = new Member();
        member.setMemberId(1L);
        member.setName("testMember");

        Study study = new Study(10, "Test");
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        StudyService studyService = new StudyService(studyRepository, memberService);
        studyService.createStudy(1L, study);

        verify(memberService, times(1)).notify(study);
        verify(memberService, never()).validate(any());

        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
        verifyNoMoreInteractions(memberService);
    }

}