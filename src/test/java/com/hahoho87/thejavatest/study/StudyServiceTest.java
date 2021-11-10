package com.hahoho87.thejavatest.study;

import com.hahoho87.thejavatest.domain.Member;
import com.hahoho87.thejavatest.domain.Study;
import com.hahoho87.thejavatest.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    StudyRepository studyRepository;
    @Mock
    MemberService memberService;

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
                .thenReturn(Optional.empty());

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

    @Test
    void applyBddStyleTest() {
        Member member = new Member();
        member.setMemberId(1L);
        member.setName("testMember");

        Study study = new Study(10, "Test");

        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        StudyService studyService = new StudyService(studyRepository, memberService);
        studyService.createStudy(1L, study);

        then(memberService).should(times(1)).notify(study);
        then(memberService).should(never()).validate(any());

        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
        then(memberService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    void openStudy() {
        // Given
        StudyService studyService = new StudyService(studyRepository, memberService);
        Study study = new Study(10, "자바 테스트");

        // TODO studRepository Mock 객체의 save 메소드 호출 시 study return
        given(studyRepository.save(any())).willReturn(study);

        // When
        studyService.openStudy(study);

        // Then
        // TODO study 의 status 가 OPENED 로 변경됐는지 확인
        assertThat(study.getStatus()).isEqualTo(StudyStatus.OPENED);
        // TODO study 의 openedDateTime 이 null 이 아닌지 확인
        assertThat(study.getOpenDateTime()).isNotNull();
        // TODO memberService 의 notify(study) 가 호출 됐는지 확인
        then(memberService).should().notify(study);

    }
}