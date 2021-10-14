package com.hahoho87.thejavatest;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

class StudyTest {

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


}