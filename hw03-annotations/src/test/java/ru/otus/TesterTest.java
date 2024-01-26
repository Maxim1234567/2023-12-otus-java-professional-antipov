package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TesterTest {

    private Result resultForFirstTestClass;

    private Result resultForSecondTestClass;

    private Result resultForThirdTestClass;

    private Result resultForFourthTestClass;

    private Result resultForFifthTestClass;

    private Result resultForSixthTestClass;

    @BeforeEach
    public void setUp() {
        resultForFirstTestClass = new Result(1, 1, 2);
        resultForSecondTestClass = new Result(2, 1, 3);
        resultForThirdTestClass = new Result(1, 1, 2);
        resultForFourthTestClass = new Result(1, 1, 2);
        resultForFifthTestClass = new Result(2, 1, 3);
        resultForSixthTestClass = new Result(0, 3, 3);
    }

    @Test
    public void shouldCorrectReturnResultForFirstTestClass() throws Exception {
        Result result = Tester.run(FirstTestClass.class);

        assertEqualsResult(resultForFirstTestClass, result);
    }

    @Test
    public void shouldCorrectWorkWithoutBeforeAndAfter() throws Exception {
        Result result = Tester.run(SecondTestClass.class);

        assertEqualsResult(resultForSecondTestClass, result);
    }

    @Test
    public void shouldCorrectWorkWithoutOnlyAfter() throws Exception {
        Result result = Tester.run(ThirdTestClass.class);

        assertEqualsResult(resultForThirdTestClass, result);
    }

    @Test
    public void shouldCorrectWorkWithoutOnlyBefore() throws Exception {
        Result result = Tester.run(FourthTestClass.class);

        assertEqualsResult(resultForFourthTestClass, result);
    }

    @Test
    public void shouldCorrectWorkIfTestMethodThrowException() throws Exception {
        Result result = Tester.run(FifthTestClass.class);

        assertEqualsResult(resultForFifthTestClass, result);
    }

    @Test
    public void shouldNotSuccessfullyTestIfExceptionThrowInBeforeMethod() throws Exception {
        Result result = Tester.run(SixthTestClass.class);

        assertEqualsResult(resultForSixthTestClass, result);
    }

    private void assertEqualsResult(Result excepted, Result result) {
        assertThat(result).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(excepted);
    }
}
