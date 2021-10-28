package com.hahoho87.thejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(FindSlowTestExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @ParameterizedTest
    @DisplayName("Parameterized 테스트")
    @ValueSource(ints = {10, 20, 30})
    void parameterizedTestWithIntegers(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println("study = " + study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertThat(Study.class).isEqualTo(targetType);
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("CsvSource 테스트")
    @ParameterizedTest
    @CsvSource({"10, '자바 스터디", "20, 스프링"})
    void csvSourceTest(int limit, String name) {
        Study study = new Study(limit, name);
        System.out.println("study = " + study);
    }

    @DisplayName("ArgumentsAccessor 테스트")
    @ParameterizedTest
    @CsvSource({"10, '자바 스터디", "20, 스프링"})
    void argumentsAccessorTest(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println("study = " + study);
    }

    @DisplayName("ArgumentAccessor 테스트 with custom aggregator")
    @ParameterizedTest
    @CsvSource({"10, '자바 스터디", "20, 스프링"})
    void argumentsAccessorTestWithCustomAggregator(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println("study = " + study);
    }

    @DisplayName("slow extension 테스트")
    @SlowTest
    void slowExtensionTest() throws InterruptedException {
        Thread.sleep(2000L);
    }


    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }
}