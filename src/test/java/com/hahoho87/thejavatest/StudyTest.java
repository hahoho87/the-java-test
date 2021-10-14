package com.hahoho87.thejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class StudyTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }


    @AfterEach
    void afterEach() {
        System.out.println("After Each");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Before Each");
    }

    @Test
    @Tag("fast")
    void createTest() {
        Study study = new Study();
        System.out.println("create1");
        assertThat(study).isNotNull();
    }

    @Test
    @Tag("slow")
    void createTest2() {
        System.out.println("create2");
    }

    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    @DisplayName("반복 테스트")
    void repeatedTest(RepetitionInfo info) {
        System.out.println(info.getCurrentRepetition() + "/" + info.getTotalRepetitions());
    }

    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @DisplayName("파라미터 변경 반복 테스트")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요."})
    void parameterizedTest(String message) {
        System.out.println("message = " + message);
    }
}